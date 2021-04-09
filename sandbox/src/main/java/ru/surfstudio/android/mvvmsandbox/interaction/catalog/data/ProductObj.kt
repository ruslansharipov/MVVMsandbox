package ru.surfstudio.android.mvvmsandbox.interaction.catalog.data

import com.google.gson.annotations.SerializedName
import ru.surfstudio.android.mvvmsandbox.domain.Price
import ru.surfstudio.android.mvvmsandbox.domain.Product
import ru.surfstudio.android.mvvmsandbox.network.transformable.Transformable
import ru.surfstudio.android.mvvmsandbox.network.transformable.transformCollection

/**
 * Мапинг-модель продукта
 */
data class ProductObj(
    @SerializedName("images") val images: List<ImageObj>?,
    @SerializedName("code") val code: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("subtitle") val subtitle: String? = null,
    @SerializedName("manufacturer") val manufacturer: String? = null,
    @SerializedName("price") val price: PriceObj? = null,
    @SerializedName("prices") val prices: List<PriceObj>? = null,
    @SerializedName("discountPrice") val discountPrice: PriceObj? = null,
    @SerializedName("listingImage") val listingImage: ImageObj? = null,
    @SerializedName("addedToWishlist") val addedToWishlist: Boolean? = null
) : Transformable<Product> {

    override fun transform(): Product {
        return Product(
            code = code ?: "",
            name = name ?: "",
            description = description ?: "",
            descriptionShort = subtitle ?: "",
            manufacturer = manufacturer ?: "",
            price = price?.transform() ?: discountPrice?.transform() ?: prices.transformCollection().firstOrNull() ?: Price(),
            addedToWishlist = addedToWishlist ?: false,
            image = listingImage?.transform() ?: ""
        )
    }
}
