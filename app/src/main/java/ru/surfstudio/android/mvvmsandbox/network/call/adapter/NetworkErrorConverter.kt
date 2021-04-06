package ru.surfstudio.android.mvvmsandbox.network.call.adapter

import retrofit2.Call
import retrofit2.HttpException
import ru.surfstudio.android.mvvmsandbox.network.error.NoInternetException
import java.io.IOException

/**
 * Класс, конвертирующий ошибку сетевых запросов в NoInternetException
 * и другие виды ошибок, в зависимости от нужд приложения
 */
class NetworkErrorConverter {

    fun <R> convertNetworkError(e: Throwable, call: Call<R>): Throwable {
        return when (e) {
//          TODO добавить необходимые типы ошибок
            is IOException -> NoInternetException(e)
            is HttpException -> onHttpException(e, call)
            else -> e
        }
    }

    private fun <R> onHttpException(e: HttpException, call: Call<R>): Throwable {
//        TODO по http коду ошибки определять какую именно ошибку возвращать
//        val response = e.response()?.raw()
//        val url = response?.request?.url?.toString() ?: EMPTY_STRING
//
//        val httpError: NetworkException = when (e.code()) {
//            CODE_400, CODE_403, CODE_404 -> getApiException(e, url)
//            CODE_401 -> NonAuthorizedException(e, url)
//            CODE_304 -> NotModifiedException(e, e.code(), url)
//            else -> OtherHttpException(e, url)
//        }
        return e
    }
}