package ru.surfstudio.android.mvvmsandbox.feature.categories

import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder
import ru.surfstudio.android.mvvmsandbox.R
import ru.surfstudio.android.mvvmsandbox.domain.Category

/**
 * Контроллер категории
 */
class CategoryController(
    private val onCategoryClick: (category: Category) -> Unit
) : BindableItemController<Category, CategoryController.Holder>() {

    override fun getItemId(data: Category): Any {
        return data.code.hashCode()
    }

    override fun createViewHolder(parent: ViewGroup): Holder {
        return Holder(parent)
    }

    inner class Holder(
        parent: ViewGroup
    ) : BindableViewHolder<Category>(parent, R.layout.list_item_category) {

        private val categoryTv = itemView.findViewById<TextView>(R.id.category_name_tv)
        private lateinit var category: Category

        init {
            itemView.setOnClickListener { onCategoryClick(category) }
        }

        override fun bind(category: Category) {
            this.category = category
            categoryTv.text = category.name

            val colorTv = ContextCompat.getColor(
                itemView.context,
                if (category.isCodeSale()) R.color.design_default_color_primary else R.color.black
            )
            categoryTv.setTextColor(colorTv)
        }
    }
}
