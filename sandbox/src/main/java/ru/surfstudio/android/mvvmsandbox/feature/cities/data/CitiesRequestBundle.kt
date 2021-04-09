package ru.surfstudio.android.mvvmsandbox.feature.cities.data

import ru.surfstudio.android.core.mvp.binding.rx.request.Request
import ru.surfstudio.android.mvvmsandbox.domain.City
import ru.surfstudio.android.mvvmsandbox.network.error.NoInternetException

internal data class CitiesRequestBundle(
        val ruCities: Request<List<City>>,
        val byCities: Request<List<City>>,
        val kzCities: Request<List<City>>
) {
    val isLoading: Boolean = ruCities.isLoading || byCities.isLoading || kzCities.isLoading

    val hasAnyData: Boolean = ruCities.getDataOrNull() != null
            || byCities.getDataOrNull() != null
            || kzCities.getDataOrNull() != null

    val hasErrors: Boolean = ruCities.getErrorOrNull() != null
            || byCities.getErrorOrNull() != null
            || kzCities.getErrorOrNull() != null

    val allErrors: Boolean = ruCities.isError && byCities.isError && kzCities.isError

    val allNoInternet: Boolean = ruCities.getErrorOrNull() is NoInternetException
            && byCities.getErrorOrNull() is NoInternetException
            && kzCities.getErrorOrNull() is NoInternetException
}