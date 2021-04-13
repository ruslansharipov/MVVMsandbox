package ru.surfstudio.standard.i_network.network

import kotlinx.coroutines.flow.Flow
import ru.surfstudio.standard.i_network.network.interactor.BaseNetworkInteractor
import ru.surfstudio.standard.i_network.network.interactor.ConnectionChecker

class TestNetworkInteractor(
        override val connectionChecker: ConnectionChecker
) : BaseNetworkInteractor {

    fun <T> testHybridQueryProxy(
            priority: DataStrategy = DataStrategy.CACHE,
            requestCreator: suspend (queryMode: Int) -> T
    ): Flow<T> = hybridQueryWithSimpleCache(priority, requestCreator)

}