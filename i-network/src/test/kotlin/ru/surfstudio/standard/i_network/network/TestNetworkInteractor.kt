package ru.surfstudio.standard.i_network.network

import kotlinx.coroutines.flow.Flow
import ru.surfstudio.android.connection.ConnectionProvider

class TestNetworkInteractor : BaseNetworkInteractor() {

    private val dataSource = TestNetworkDataSource()

    override val connectionChecker: ConnectionChecker = TestConnectionChecker()

    fun getDataWithSimpleCache() : Flow<String> {
        return hybridQueryWithSimpleCache { dataSource.getData(it) }
    }

}