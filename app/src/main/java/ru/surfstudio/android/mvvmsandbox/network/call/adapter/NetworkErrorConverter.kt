package ru.surfstudio.android.mvvmsandbox.network.call.adapter

import retrofit2.Call
import retrofit2.HttpException
import ru.surfstudio.android.mvvmsandbox.network.error.*
import ru.surfstudio.android.mvvmsandbox.network.HttpCodes.CODE_304
import ru.surfstudio.android.mvvmsandbox.network.HttpCodes.CODE_400
import ru.surfstudio.android.mvvmsandbox.network.HttpCodes.CODE_401
import ru.surfstudio.android.mvvmsandbox.network.HttpCodes.CODE_403
import ru.surfstudio.android.mvvmsandbox.network.HttpCodes.CODE_404
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING
import java.io.IOException

/**
 * Класс, конвертирующий ошибку сетевых запросов в NoInternetException
 * и другие виды ошибок, в зависимости от нужд приложения
 */
class NetworkErrorConverter {

    fun <R> convertNetworkError(e: Throwable, call: Call<R>): Throwable {
        return when (e) {
            is IOException -> NoInternetException(e)
            is HttpException -> onHttpException(e, call)
            else -> e
        }
    }

    private fun <R> onHttpException(e: HttpException, call: Call<R>): Throwable {
        val response = e.response()?.raw()
        val url = response?.request?.url?.toString() ?: EMPTY_STRING
        val httpError: NetworkException = when (e.code()) {
            CODE_400, CODE_403, CODE_404 -> getApiException(e, url)
            CODE_401 -> NonAuthorizedException(e, url)
            CODE_304 -> NotModifiedException(e, url)
            else -> OtherHttpException(e, url)
        }
        return httpError
    }

    private fun getApiException(e: HttpException, url: String): NetworkException {
        // TODO парсинг тела ответа сервера и логика получения из него ошибки
        // val responseBody = e.response()?.errorBody()
        return OtherHttpException(e, url)
    }
}