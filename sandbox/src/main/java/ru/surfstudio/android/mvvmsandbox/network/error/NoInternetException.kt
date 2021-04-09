package ru.surfstudio.android.mvvmsandbox.network.error

/**
 * Ошибка отсутствия интернета
 */
class NoInternetException(cause: Throwable) : NetworkException(cause)