package ru.surfstudio.standard.i_network.network

import ru.surfstudio.standard.i_network.network.interactor.BaseNetworkInteractor
import ru.surfstudio.standard.i_network.network.interactor.ConnectionChecker

class TestNetworkInteractor(
        override val connectionChecker: ConnectionChecker
) : BaseNetworkInteractor