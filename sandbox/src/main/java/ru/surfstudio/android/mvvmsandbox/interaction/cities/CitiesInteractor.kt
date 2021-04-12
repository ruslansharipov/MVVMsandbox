package ru.surfstudio.android.mvvmsandbox.interaction.cities

import ru.surfstudio.android.mvvmsandbox.domain.City
import javax.inject.Inject
import ru.surfstudio.android.dagger.scope.PerApplication

@PerApplication
class CitiesInteractor @Inject constructor(
        private val citiesApi: CitiesApi
) {

    suspend fun getCities(countryCode: String) : List<City> {
        return citiesApi.getCitiesBy(countryCode)
                .values
                .flatten()
                .map { it.transform() }
    }
}