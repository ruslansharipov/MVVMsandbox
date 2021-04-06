package ru.surfstudio.android.mvvmsandbox.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import ru.surfstudio.android.core.mvp.binding.rx.request.Request

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

fun <T> requestFlow(requestFunc: suspend () -> T): Flow<Request<T>> {
    return flow {
        emit(Request.Loading())
        emit(Request.Success(requestFunc()))
    }.catch { error ->
        emit(Request.Error(error))
    }
}