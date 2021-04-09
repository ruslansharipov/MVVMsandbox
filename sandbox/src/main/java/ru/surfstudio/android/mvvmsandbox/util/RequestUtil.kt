package ru.surfstudio.android.mvvmsandbox.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import ru.surfstudio.android.core.mvp.binding.rx.request.Request

/**
 * Обертка над запросом в сеть, возвращающая данные в виде [Request.Success] в случае успеха и
 * ошибку в [Request.Error], если в ходе запроса произошла ошибка
 */
suspend fun <T> request(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        requestFunc: suspend () -> T
): Request<T> {
    return withContext(dispatcher) {
        try {
            Request.Success(requestFunc())
        } catch (e: Exception) {
            Request.Error(e)
        }
    }
}

/**
 * Обертка над Flow, которая эмитит [Request.Loading] до того как основной Flow начнет свои эмиты,
 * Ошибка, полученная в ходе запроса оборачивается в [Request.Error].
 * Успешно загруженные данные приходят в виде [Request.Success]
 */
fun <T> requestFlow(requestFunc: suspend () -> T): Flow<Request<T>> {
    return flow<Request<T>> {
        emit(Request.Success(requestFunc()))
    }.onStart {
        emit(Request.Loading())
    }.catch { error ->
        emit(Request.Error(error))
    }
}