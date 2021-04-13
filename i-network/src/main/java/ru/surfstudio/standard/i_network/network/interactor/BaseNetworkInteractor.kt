package ru.surfstudio.standard.i_network.network.interactor

import kotlinx.coroutines.flow.*
import ru.surfstudio.android.logger.Logger
import ru.surfstudio.standard.i_network.error.NotModifiedException
import ru.surfstudio.standard.i_network.network.BaseServerConstants
import ru.surfstudio.standard.i_network.network.DataStrategy

interface BaseNetworkInteractor {

    val connectionChecker: ConnectionChecker

    fun <T> hybridQueryWithSimpleCache(
            priority: DataStrategy = DataStrategy.CACHE,
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

    fun <T> hybridQuery(
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
        }.catch { error ->
            if (error is CacheExceptionWrapper) {
                //в случае ошибки получения данных из кеша производим запрос на сервер
                Logger.e(error.cause, "Error when getting data from cache")
                emit(requestCreator(BaseServerConstants.QUERY_MODE_FORCE))
            } else {
                throw error
            }
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