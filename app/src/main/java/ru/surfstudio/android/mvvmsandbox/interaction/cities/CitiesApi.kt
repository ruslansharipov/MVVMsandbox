package ru.surfstudio.android.mvvmsandbox.interaction.cities

import retrofit2.http.GET
import retrofit2.http.Path
import ru.surfstudio.android.mvvmsandbox.interaction.cities.data.CityObj

interface CitiesApi {

    @GET("countries/{country}/cities")
    suspend fun getCitiesBy(@Path("country") country: String) : Map<String, List<CityObj>>
}