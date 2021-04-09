package ru.surfstudio.android.mvvmsandbox.interaction.catalog.data

import com.google.gson.annotations.SerializedName
import ru.surfstudio.android.mvvmsandbox.domain.Category
import ru.surfstudio.android.mvvmsandbox.network.transformable.Transformable

/**
 * Мапинг-модель категории каталога
 */
class CategoryObj(
    @SerializedName("code") val code: String? = null,
    @SerializedName("children") val children: List<CategoryObj>? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("rigidCategoryCode") val rigidCategoryCode: String? = null
) : Transformable<Category> {

    override fun transform(): Category {
        return Category(
            code = code ?: "",
            children = children?.map { it.transform() } ?: emptyList(),
            name = name ?: "",
            rigidCode = rigidCategoryCode?.toLongOrNull() ?: 0L
        )
    }
}