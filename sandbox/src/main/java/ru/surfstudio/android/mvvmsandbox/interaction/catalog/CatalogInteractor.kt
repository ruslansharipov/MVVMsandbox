package ru.surfstudio.android.mvvmsandbox.interaction.catalog

import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList
import ru.surfstudio.android.mvvmsandbox.domain.Category
import ru.surfstudio.android.mvvmsandbox.domain.Product
import ru.surfstudio.android.mvvmsandbox.network.transformable.transformCollection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatalogInteractor @Inject constructor(
    private val catalogApi: CatalogApi
) {

    suspend fun getCategories(): List<Category> {
        return catalogApi.getCategories().transformCollection()
    }

    suspend fun getProducts(page: Int): DataList<Product> {
        return catalogApi.getProducts(currentPage = page, pageSize = 20).transform()
    }

    suspend fun addFavorite(productCode: String) {
        return catalogApi.addFavorite(productCode)
    }

    suspend fun removeFavorite(code: String) {
        return catalogApi.addFavorite(code)
    }
}