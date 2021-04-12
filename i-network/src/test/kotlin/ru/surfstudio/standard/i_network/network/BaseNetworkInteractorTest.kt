package ru.surfstudio.standard.i_network.network

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class BaseNetworkInteractorTest {

    private val testInteractor = TestNetworkInteractor()

    @Test
    fun assertReturnsCachedAndNetworkData() = runBlocking {
        val dataList = testInteractor
                .getDataWithSimpleCache()
                .toList()

        assert(dataList.containsAll(listOf(TestNetworkDataSource.CACHED_DATA, TestNetworkDataSource.NETWORK_DATA)))
    }

}