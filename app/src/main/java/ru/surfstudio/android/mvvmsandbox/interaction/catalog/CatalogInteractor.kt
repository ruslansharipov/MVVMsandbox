package ru.surfstudio.android.mvvmsandbox.interaction.catalog

import kotlinx.coroutines.delay
import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList
import ru.surfstudio.android.mvvmsandbox.domain.Category
import ru.surfstudio.android.mvvmsandbox.domain.Product
import ru.surfstudio.android.mvvmsandbox.network.transformCollection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatalogInteractor @Inject constructor(
    private val catalogApi: CatalogApi
) {

    suspend fun getCategories(): List<Category> {
        delay(1000)
        return catalogApi.getCategories().transformCollection()
    }

    suspend fun getProducts(page: Int): DataList<Product> {
        delay(1000)
        return catalogApi.getProducts(currentPage = page, pageSize = 20).transform()
    }

    suspend fun addFavorite(productCode: String) {
        delay(1000)
        return catalogApi.addFavorite(productCode)
    }

    suspend fun removeFavorite(code: String) {
        delay(1000)
        return catalogApi.addFavorite(code)
    }
}