package ru.surfstudio.standard.i_network.network

class TestNetworkDataSource {

    suspend fun getData(queryMode: Int): String {
        return if (queryMode == BaseServerConstants.QUERY_MODE_FROM_SIMPLE_CACHE) {
            CACHED_DATA
        } else {
            NETWORK_DATA
        }
    }

    companion object {
        const val CACHED_DATA = "CACHED_DATA"
        const val NETWORK_DATA = "NETWORK_DATA"
    }
}