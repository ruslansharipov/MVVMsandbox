package ru.surfstudio.android.mvvmsandbox.feature.products

import android.view.ViewGroup
import androidx.lifecycle.ViewModelStore
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder
import ru.surfstudio.android.mvvmsandbox.R
import ru.surfstudio.android.mvvmsandbox.domain.Product
import ru.surfstudio.android.mvvmsandbox.widget.ProductWidget

/**
 *  Контроллер продукта
 */
class ProductViewController(
        private val viewModelStore: ViewModelStore
) : BindableItemController<Product, ProductViewController.Holder>() {

    override fun getItemId(data: Product): Any {
        return data.code
    }

    override fun createViewHolder(parent: ViewGroup): Holder {
        return Holder(parent)
    }

    inner class Holder(parent: ViewGroup) : BindableViewHolder<Product>(parent, R.layout.list_item_product_widget) {

        private val productWidget: ProductWidget = itemView.findViewById(R.id.product_widget)

        override fun bind(data: Product) {
            productWidget.bindData(viewModelStore, data)
        }
    }
}
