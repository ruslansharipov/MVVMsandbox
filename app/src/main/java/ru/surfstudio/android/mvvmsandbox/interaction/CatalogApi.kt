package ru.surfstudio.android.mvvmsandbox.interaction

import retrofit2.http.GET
import ru.surfstudio.android.mvvmsandbox.interaction.data.CategoryObj

interface CatalogApi {

    @GET("catalog")
    suspend fun getCategories(): List<CategoryObj>
}