package ru.surfstudio.standard.i_network.network

import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import ru.surfstudio.standard.i_network.error.CacheEmptyException
import ru.surfstudio.standard.i_network.error.NoInternetException
import ru.surfstudio.standard.i_network.error.NotModifiedException
import java.io.IOException

class TestNetworkDataSource {

    suspend fun getDataFromCacheAndNetwork(queryMode: Int): String {
        return if (queryMode == BaseServerConstants.QUERY_MODE_FROM_SIMPLE_CACHE) {
            CACHED_DATA
        } else {
            NETWORK_DATA
        }
    }

    suspend fun getDataWithEmptyCache(queryMode: Int): String {
        return if (queryMode == BaseServerConstants.QUERY_MODE_FROM_SIMPLE_CACHE) {
            throw CacheEmptyException()
        } else {
            NETWORK_DATA
        }
    }

    suspend fun getFromCacheThrowFromNetwork(queryMode: Int): String {
        return if (queryMode == BaseServerConstants.QUERY_MODE_FROM_SIMPLE_CACHE) {
            CACHED_DATA
        } else {
            throw NoInternetException(IOException())
        }
    }

    suspend fun getFromServerIfModified(queryMode: Int): String {
        return if (queryMode == BaseServerConstants.QUERY_MODE_ONLY_IF_CHANGED) {
            throw NotModifiedException(HttpException(Response.error<String>(444, "".toResponseBody())), "dummy url")
        } else {
            NETWORK_DATA
        }
    }

    suspend fun throwNoInternetError(queryMode: Int) : String {
        throw NoInternetException(IOException())
    }


    companion object {
        const val CACHED_DATA = "CACHED_DATA"
        const val NETWORK_DATA = "NETWORK_DATA"
    }
}