package ru.surfstudio.standard.i_network.error

/**
 * Ошибка отсутствия интернета
 */
class NoInternetException(cause: Throwable) : NetworkException(cause)