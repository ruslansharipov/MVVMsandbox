package ru.surfstudio.standard.i_network.network

import kotlinx.coroutines.flow.*
import ru.surfstudio.android.connection.ConnectionProvider
import ru.surfstudio.standard.i_network.error.NotModifiedException

abstract class BaseNetworkInteractor {

    protected abstract val connectionChecker: ConnectionChecker

    protected fun <T> hybridQueryWithSimpleCache(
            priority: DataStrategy = DataStrategy.DEFAULT_DATA_STRATEGY,
            requestCreator: suspend (queryMode: Int) -> T
    ): Flow<T> {
        return hybridQuery(
                priority = priority,
                cacheFlow = flow {
                    emit(requestCreator(BaseServerConstants.QUERY_MODE_FROM_SIMPLE_CACHE))
                },
                requestCreator = requestCreator
        )
    }

    protected fun <T> hybridQuery(
            priority: DataStrategy,
            cacheFlow: Flow<T>,
            requestCreator: suspend (queryMode: Int) -> T
    ): Flow<T> {
        return cacheFlow.catch {
            throw CacheExceptionWrapper(it)
        }.flatMapConcat { cache: T ->
            val queryMode = BaseServerConstants.QUERY_MODE_ONLY_IF_CHANGED
            val isAutoAndConnectionIsSlow = priority == DataStrategy.AUTO && !connectionChecker.isConnectedFast
            getDataFlow(
                    cacheFirst = isAutoAndConnectionIsSlow || priority == DataStrategy.CACHE,
                    onlyActual = priority == DataStrategy.ONLY_ACTUAL,
                    cacheFlow = flowOf(cache),
                    networkFlow = flow {
                        emit(requestCreator(queryMode))
                    }
            )
        }
    }

    private fun <T> getDataFlow(
            cacheFirst: Boolean,
            onlyActual: Boolean,
            cacheFlow: Flow<T>,
            networkFlow: Flow<T>
    ): Flow<T> {
        return when {
            cacheFirst -> {
                cacheFlow.onCompletion {
                    emitAll(
                            networkFlow.catch { error ->
                                emitAll(processNetworkException(error))
                            }
                    )
                }
            }
            onlyActual -> {
                networkFlow.catch { error ->
                    if (error is NotModifiedException) {
                        emitAll(cacheFlow)
                    } else {
                        throw error
                    }
                }
            }
            else -> {
                networkFlow.catch { error ->
                    emitAll(cacheFlow)
                    emitAll(processNetworkException(error))
                }
            }
        }
    }

    private fun <T> processNetworkException(e: Throwable): Flow<T> {
        return if (e is NotModifiedException) {
            flowOf()
        } else {
            flow { throw e }
        }
    }

    /**
     * Оборачивает ошибку, полученную при получении данных из кеша
     */
    private class CacheExceptionWrapper constructor(cause: Throwable?) : Exception(cause)

}

class ConnectionCheckerProvider(
        private val provider: ConnectionProvider
) : ConnectionChecker {

    override val isConnectedFast: Boolean
        get() = provider.isConnectedFast

}

interface ConnectionChecker {
    val isConnectedFast: Boolean
}