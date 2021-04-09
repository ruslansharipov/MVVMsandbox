package ru.surfstudio.android.mvvmsandbox.network.error

/**
 * Ошибка конвертирования тела запроса
 */
class ConversionException(message: String, cause: Throwable) : NetworkException(message, cause)