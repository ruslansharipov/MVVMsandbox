package ru.surfstudio.standard.f_main.widget

import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModelStore
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder
import ru.surfstudio.standard.f_main.R
import ru.surfstudio.standard.f_main.widget.data.ProductUi

/**
 * Контроллер виджета
 */
class ProductViewController(
        private val viewModelStore: ViewModelStore,
        private val lifecycleScope: LifecycleCoroutineScope
) : BindableItemController<ProductUi, ProductViewController.Holder>() {

    override fun getItemId(data: ProductUi): Any {
        return data.id
    }

    override fun createViewHolder(parent: ViewGroup): Holder {
        return Holder(parent)
    }

    inner class Holder(parent: ViewGroup) : BindableViewHolder<ProductUi>(parent, R.layout.list_item_product_widget) {

        private val productWidget: ProductWidget = itemView.findViewById(R.id.product_widget)

        override fun bind(data: ProductUi) {
            productWidget.bindData(data, viewModelStore, lifecycleScope)
        }
    }
}