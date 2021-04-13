package ru.surfstudio.standard.i_network.network.interactor

/**
 * Интерфейс сущности, проверяющей скорость соединения
 */
interface ConnectionChecker {
    val isConnectedFast: Boolean
}