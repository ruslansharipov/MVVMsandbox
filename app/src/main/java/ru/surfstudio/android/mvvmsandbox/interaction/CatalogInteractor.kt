package ru.surfstudio.android.mvvmsandbox.interaction

import ru.surfstudio.android.mvvmsandbox.domain.Category
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatalogInteractor @Inject constructor(
        private val catalogApi: CatalogApi
) {

    suspend fun getCategories(): List<Category> {
        return catalogApi.getCategories().map { it.transform() }
    }
}