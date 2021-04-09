package ru.surfstudio.android.mvvmsandbox.interaction.catalog.data

import com.google.gson.annotations.SerializedName
import ru.surfstudio.android.mvvmsandbox.domain.Pagination
import ru.surfstudio.android.mvvmsandbox.network.transformable.Transformable

/**
 * Мапинг-модель описания пагинации
 */
data class PaginationObj(
    @SerializedName("totalResults") val totalResults: Int? = null,
    @SerializedName("totalPages") val totalPages: Int? = null,
    @SerializedName("pageSize") val pageSize: Int? = null,
    @SerializedName("currentPage") val currentPage: Int? = null
) : Transformable<Pagination> {

    override fun transform(): Pagination {
        return Pagination(
                totalResults = totalResults ?: 0,
                totalPages = totalPages ?: 0,
                pageSize = pageSize ?: 0,
                currentPage = currentPage ?: 0
        )
    }
}
