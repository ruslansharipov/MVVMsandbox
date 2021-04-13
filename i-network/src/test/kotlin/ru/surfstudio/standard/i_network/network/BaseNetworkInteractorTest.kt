package ru.surfstudio.standard.i_network.network

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldContainExactly
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.assertThrows
import ru.surfstudio.standard.i_network.error.NoInternetException
import ru.surfstudio.standard.i_network.network.interactor.ConnectionChecker

internal class BaseNetworkInteractorTest : AnnotationSpec() {

    private val dataSource = TestNetworkDataSource()

    @Test
    fun `returns cached and network data`() = runBlocking {
        val cacheDataList = createInteractor()
                .hybridQueryWithSimpleCache(DataStrategy.CACHE) { dataSource.getDataFromCacheAndNetwork(it) }
                .toList()

        assertSoftly {
            cacheDataList shouldContainExactly listOf(TestNetworkDataSource.CACHED_DATA, TestNetworkDataSource.NETWORK_DATA)
        }
    }

    @Test
    fun `returns only network data on auto when connection fast`() = runBlocking {
        val autoDataList = createInteractor()
                .hybridQueryWithSimpleCache(DataStrategy.AUTO) { dataSource.getDataFromCacheAndNetwork(it) }
                .toList()
        assertSoftly {
            autoDataList shouldContainExactly listOf(TestNetworkDataSource.NETWORK_DATA)
        }
    }

    @Test
    fun `returns cache and network data on auto when connection slow`() = runBlocking {
        val autoDataList = createInteractor(isConnectedFast = false)
                .hybridQueryWithSimpleCache(DataStrategy.AUTO) { dataSource.getDataFromCacheAndNetwork(it) }
                .toList()
        assertSoftly {
            autoDataList shouldContainExactly listOf(TestNetworkDataSource.CACHED_DATA, TestNetworkDataSource.NETWORK_DATA)
        }
    }

    @Test
    fun `returns only network data`() = runBlocking {
        val dataList = createInteractor()
                .hybridQueryWithSimpleCache { dataSource.getDataWithEmptyCache(it) }
                .toList()
        assertSoftly {
            dataList shouldContainExactly listOf(TestNetworkDataSource.NETWORK_DATA)
        }
    }

    @Test
    fun `assert throws error from network`() {
        assertThrows<NoInternetException> {
            runBlocking {
                createInteractor()
                        .hybridQueryWithSimpleCache { dataSource.getFromCacheThrowFromNetwork(it) }
                        .toList()
            }
        }
    }

    @Test
    fun `returns cached data when not modified`() = runBlocking {
        val dataList = createInteractor()
                .hybridQueryWithSimpleCache(DataStrategy.ONLY_ACTUAL) { queryMode: Int ->
                    dataSource.getFromServerIfModified(queryMode)
                }.toList()
        assertSoftly {
            dataList shouldContainExactly listOf(TestNetworkDataSource.NETWORK_DATA)
        }
    }

    @Test
    fun `assert only actual throws with no internet`() {
        assertThrows<NoInternetException> {
            runBlocking {
                createInteractor().hybridQueryWithSimpleCache(DataStrategy.ONLY_ACTUAL) { queryMode: Int ->
                    dataSource.throwNoInternetError(queryMode)
                }.toList()
            }
        }
    }

    @Test
    fun `force network request contains network data`() = runBlocking {
        val dataList = createInteractor()
                .hybridQueryWithSimpleCache(DataStrategy.SERVER) { dataSource.getDataFromCacheAndNetwork(it) }
                .toList()
        assertSoftly {
            dataList shouldContainExactly listOf(TestNetworkDataSource.NETWORK_DATA)
        }
    }

    private fun createInteractor(isConnectedFast: Boolean = true): TestNetworkInteractor {
        return TestNetworkInteractor(
                object : ConnectionChecker {
                    override val isConnectedFast: Boolean
                        get() = isConnectedFast
                }
        )
    }
}