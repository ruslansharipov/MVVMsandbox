package ru.surfstudio.android.mvvmsandbox.feature.products

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder
import ru.surfstudio.android.imageloader.ImageLoader
import ru.surfstudio.android.mvvmsandbox.R
import ru.surfstudio.android.mvvmsandbox.domain.Product

/**
 *  Контроллер продукта
 */
class ProductViewController(
    private val onProductClick: (Product) -> Unit,
    private val onFavoriteClick: (Product) -> Unit
) : BindableItemController<Product, ProductViewController.Holder>() {

    override fun getItemId(data: Product): Any {
        return data.code
    }

    override fun createViewHolder(parent: ViewGroup): Holder {
        return Holder(parent)
    }

    inner class Holder(parent: ViewGroup) : BindableViewHolder<Product>(parent, R.layout.list_item_product_view) {

        private lateinit var product: Product
        private val photoIv: ImageView = itemView.findViewById(R.id.product_photo_iv)
        private val addToFavoriteIv: ImageView = itemView.findViewById(R.id.product_add_favorite_iv)
        private val nameTv: TextView = itemView.findViewById(R.id.product_name_tv)
        private val priceTv: TextView = itemView.findViewById(R.id.product_price_tv)
        private val shortDescriptionTv: TextView = itemView.findViewById(R.id.product_short_description_tv)

        init {
            itemView.setOnClickListener { onProductClick(product) }
            addToFavoriteIv.setOnClickListener { onFavoriteClick(product) }
        }

        override fun bind(data: Product) {
            this.product = data
            ImageLoader.with(itemView.context)
                .error(drawableResId = R.drawable.ic_product_place_holder)
                .preview(drawableResId = R.drawable.ic_product_place_holder)
                .url(product.image)
                .into(photoIv)

            addToFavoriteIv.isSelected = product.addedToWishlist
            nameTv.text = product.name
            shortDescriptionTv.text = product.descriptionShort
            priceTv.text = product.price.value.toString()
        }
    }
}
