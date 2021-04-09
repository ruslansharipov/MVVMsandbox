package ru.surfstudio.standard.i_network.error

/**
 * Внутренне исключение для механизма работы с сервером, используется для возвращения null в случае
 * попытки получения кеша, которого не существует
 */
class CacheEmptyException: NetworkException()