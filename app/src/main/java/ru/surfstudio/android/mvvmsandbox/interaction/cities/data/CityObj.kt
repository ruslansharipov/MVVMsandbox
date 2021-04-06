package ru.surfstudio.android.mvvmsandbox.interaction.cities.data

import com.google.gson.annotations.SerializedName
import ru.surfstudio.android.mvvmsandbox.domain.City
import ru.surfstudio.android.mvvmsandbox.network.transformable.Transformable
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING

/**
 * Сериализуемая модель города [City]
 */
class CityObj(
    @SerializedName("code") val code: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("fiasId") val fiasId: String? = null,
    @SerializedName("kladrId") val kladrId: String? = null
) : Transformable<City> {

    override fun transform(): City = City(
        code = code ?: EMPTY_STRING,
        cityName = name ?: EMPTY_STRING,
        fiasId = fiasId ?: EMPTY_STRING,
        kladrId = kladrId ?: EMPTY_STRING
    )
}
