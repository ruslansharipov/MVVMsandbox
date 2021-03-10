package ru.surfstudio.android.mvvmsandbox.interaction.catalog.data

import com.google.gson.annotations.SerializedName
import ru.surfstudio.android.mvvmsandbox.domain.Price
import ru.surfstudio.android.mvvmsandbox.network.Transformable

/**
 * Мапинг-модель цены
 */
data class PriceObj(
    @SerializedName("value") val value: Double? = null
) : Transformable<Price> {

    override fun transform(): Price {
        return Price(
                value = value ?: .0
        )
    }
}
