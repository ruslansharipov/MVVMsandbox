package ru.surfstudio.android.mvvmsandbox.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class Request<T> {
    class Loading<T> : Request<T>()
    data class Success<T>(internal val data: T) : Request<T>()
    data class Error<T>(internal val error: Throwable) : Request<T>()
}

suspend fun <T> request(requestFunc: suspend () -> T): Request<T> {
    return withContext(Dispatchers.IO) {
        try {
            Request.Success(requestFunc())
        } catch (e: Exception) {
            Request.Error(e)
        }
    }
}