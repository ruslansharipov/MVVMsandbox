package ru.surfstudio.standard.i_network.error

/**
 * Ошибка конвертирования тела запроса
 */
class ConversionException(message: String, cause: Throwable) : NetworkException(message, cause)