package ru.surfstudio.standard.i_network.network.interactor

import ru.surfstudio.android.connection.ConnectionProvider

/**
 * Сущность, проверяющая скорость соединения, основываясь на данных предоставляемых [provider]
 */
class ConnectionCheckerProvider(
        private val provider: ConnectionProvider
) : ConnectionChecker {

    override val isConnectedFast: Boolean
        get() = provider.isConnectedFast

}