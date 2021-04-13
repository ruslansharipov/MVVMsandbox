package ru.surfstudio.standard.i_network.network.interactor

import kotlinx.coroutines.flow.*
import ru.surfstudio.android.logger.Logger
import ru.surfstudio.standard.i_network.error.NotModifiedException
import ru.surfstudio.standard.i_network.network.BaseServerConstants
import ru.surfstudio.standard.i_network.network.DataStrategy

/**
 * Интерактор поддерживающий гибридные запросы
 */
interface BaseNetworkInteractor {

    /**
     * Предоставляет данные о скорости соединения
     */
    val connectionChecker: ConnectionChecker

    /**
     * Гибридный запрос с использованием SimpleCache
     */
    fun <T> hybridQueryWithSimpleCache(
            priority: DataStrategy = DataStrategy.CACHE,
            requestCreator: suspend (queryMode: Int) -> T
    ): Flow<T> {
        return hybridQuery(
                priority = priority,
                cacheRequestCreator = { requestCreator(BaseServerConstants.QUERY_MODE_FROM_SIMPLE_CACHE) },
                networkRequestCreator = requestCreator
        )
    }

    /**
     * @param priority стратегия, определяющая порядок запросов
     * @param cacheRequestCreator suspend функция, возвращающая данные из кеша
     * @param networkRequestCreator suspend функция, возвращающая данные из сети
     *
     * @return [Flow] в который отправляются данные в зависимости от передаваемого приоритета.
     *
     * DataStrategy.CACHE - сначала эмитятся данные из кеша, если они есть и после этого проиходит
     * запрос в сеть
     *
     * DataStrategy.SERVER - сразу производится запрос в сеть, данные из кеша эмитятся в случае
     * ошибки запроса, и если есть закешированные данные
     *
     * DataStrategy.AUTO - если скорость соединения с интернетом высокая, то логика как в случае
     * с DataStrategy.SERVER, если скорость низкая, то как с DataStrategy.CACHE
     *
     * DataStrategy.ONLY_ACTUAL - стратегия для использованием совместно с механизмом etag -
     * производится запрос в сеть и если получаем ошибку о том, что данные не изменились - эмитим
     * данные из кэша, иначе эмитятся полученные от сервера данные
     */
    fun <T> hybridQuery(
            priority: DataStrategy = DataStrategy.CACHE,
            cacheRequestCreator: suspend (queryMode: Int) -> T,
            networkRequestCreator: suspend (queryMode: Int) -> T
    ): Flow<T> {
        return flow<T> {
            emit(cacheRequestCreator(BaseServerConstants.QUERY_MODE_FROM_SIMPLE_CACHE))
        }.catch {
            throw CacheExceptionWrapper(it)
        }.flatMapConcat { cache: T ->
            val queryMode = BaseServerConstants.QUERY_MODE_ONLY_IF_CHANGED
            val isAutoAndConnectionIsSlow = priority == DataStrategy.AUTO && !connectionChecker.isConnectedFast
            getDataFlow(
                    cacheFirst = isAutoAndConnectionIsSlow || priority == DataStrategy.CACHE,
                    onlyActual = priority == DataStrategy.ONLY_ACTUAL,
                    cacheFlow = flowOf(cache),
                    networkFlow = flow {
                        emit(networkRequestCreator(queryMode))
                    }
            )
        }.catch { error ->
            if (error is CacheExceptionWrapper) {
                //в случае ошибки получения данных из кеша производим запрос на сервер
                Logger.e(error.cause, "Error when getting data from cache")
                emit(networkRequestCreator(BaseServerConstants.QUERY_MODE_FORCE))
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