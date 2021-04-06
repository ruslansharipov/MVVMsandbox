package ru.surfstudio.android.mvvmsandbox.network.error

/**
 * Базовый класс ошибки сетевого запроса
 */
open class NetworkException(cause: Throwable): RuntimeException(cause)