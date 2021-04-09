package ru.surfstudio.android.mvvmsandbox.network.error

/**
 * Базовый класс ошибки сетевого запроса
 */
abstract class NetworkException : RuntimeException {
    constructor()
    constructor(message: String?) : super(message)
    constructor(cause: Throwable?) : super(cause)
    constructor(message: String, cause: Throwable) : super(message, cause)
}