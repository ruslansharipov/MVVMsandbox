package ru.surfstudio.android.mvvmsandbox.interaction.catalog.data

import com.google.gson.annotations.SerializedName
import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList
import ru.surfstudio.android.mvvmsandbox.domain.Product
import ru.surfstudio.android.mvvmsandbox.network.Transformable
import ru.surfstudio.android.mvvmsandbox.network.toDataList
import ru.surfstudio.android.mvvmsandbox.network.transformCollection

/**
 * Мапинг-модель продуктов с пагинацией
 */
data class ProductsResponse(
    @SerializedName("pagination") val pagination: PaginationObj? = null,
    @SerializedName("results") val products: List<ProductObj>? = null
) : Transformable<DataList<Product>> {

    override fun transform(): DataList<Product> {
        return products.transformCollection().toDataList(pagination)
    }
}
