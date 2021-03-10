package ru.surfstudio.android.mvvmsandbox.interaction.catalog

import retrofit2.http.GET
import retrofit2.http.Query
import ru.surfstudio.android.mvvmsandbox.interaction.catalog.data.CategoryObj
import ru.surfstudio.android.mvvmsandbox.interaction.catalog.data.ProductsResponse

interface CatalogApi {

    @GET("catalog")
    suspend fun getCategories(): List<CategoryObj>

    @GET("products")
    suspend fun getProducts(
        @Query("currentPage") currentPage: Int,
        @Query("pageSize") pageSize: Int
    ): ProductsResponse
}