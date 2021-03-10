package ru.surfstudio.android.mvvmsandbox.interaction.catalog.data

import com.google.gson.annotations.SerializedName
import ru.surfstudio.android.mvvmsandbox.network.Transformable

/**
 * Мапинг-модель изображения
 */
class ImageObj(
    @SerializedName("url") val url: String? = null
) : Transformable<String> {

    override fun transform(): String {
        return "https://upstage.rivegauche.ru/$url"
    }
}
