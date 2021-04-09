package ru.surfstudio.standard.i_network.error

import retrofit2.HttpException

private fun prepareMessage(message: String, code: Int, url: String): String {
    return " httpCode=" + code + "\n" +
        ", Message='" + message + "'" +
        ", url='" + url + "'"
}

/**
 * получен ответ не 2xx
 */
sealed class HttpProtocolException(
    cause: HttpException,
    url: String,
    val errorMessage: String = cause.message(),
    val httpCode: Int = cause.code()
) : NetworkException(prepareMessage(errorMessage, httpCode, url), cause)

/**
 * ошибка 304, см механизм Etag
 */
class NotModifiedException(
        cause: HttpException,
        url: String
) : HttpProtocolException(cause, url)

/**
 * Отсутствует авторизация(401)
 */
class NonAuthorizedException(
    cause: HttpException,
    url: String
) : HttpProtocolException(cause, url)

/**
 * Неизвестная ошибка
 */
class OtherHttpException(
    cause: HttpException,
    url: String
) : HttpProtocolException(cause, "Неизвестная ошибка сервера", url)
