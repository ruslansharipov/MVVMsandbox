package ru.surfstudio.android.mvvmsandbox.feature.cities

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.surfstudio.android.core.mvp.binding.rx.request.data.RequestUi
import ru.surfstudio.android.mvvmsandbox.domain.City
import ru.surfstudio.android.mvvmsandbox.feature.cities.data.CitiesRequestBundle
import ru.surfstudio.android.mvvmsandbox.feature.cities.data.PlaceholderState
import ru.surfstudio.android.mvvmsandbox.feature.di.ScreenScope
import ru.surfstudio.android.mvvmsandbox.interaction.cities.CitiesInteractor
import ru.surfstudio.android.mvvmsandbox.navigation.view.model.NavigationViewModel
import ru.surfstudio.android.mvvmsandbox.util.requestFlow
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import javax.inject.Inject

@ScreenScope
class CitiesViewModelImpl @Inject constructor(
        private val citiesInteractor: CitiesInteractor,
        override val navCommandExecutor: AppCommandExecutor
) : ViewModel(), CitiesViewModel, NavigationViewModel {

    private val TAG = "CitiesViewModel"

    override val cities = MutableStateFlow<RequestUi<List<City>>>(RequestUi())
    override val toast = MutableSharedFlow<String>()

    init {
        loadCities()
    }

    override fun retry() {
        loadCities()
    }

    private fun loadCities() {
        viewModelScope.launch {
            combine(
                    requestFlow { citiesInteractor.getCities("RU") },
                    requestFlow { citiesInteractor.getCities("BY") },
                    requestFlow { citiesInteractor.getCities("KZ") }
            ) { ruRequest, byRequest, kzRequest ->
                CitiesRequestBundle(ruRequest, byRequest, kzRequest)
            }
                    .flowOn(Dispatchers.IO)
                    .collect { bundle: CitiesRequestBundle ->
                        handleCitiesState(bundle)
                        handleCitiesError(bundle)
                    }
        }
    }

    private suspend fun handleCitiesError(bundle: CitiesRequestBundle) {
        Log.d(TAG, "bundle.hasErrors = ${bundle.hasErrors}")
        if (!bundle.isLoading && bundle.hasErrors) {
            val ruError = bundle.ruCities.getErrorOrNull()
            val byError = bundle.byCities.getErrorOrNull()
            val kzError = bundle.kzCities.getErrorOrNull()

            val errors = mutableListOf<String>()

            if (ruError != null) {
                errors.add("России")
            }
            if (byError != null) {
                errors.add("Беларуси")
            }
            if (kzError != null) {
                errors.add("Казахстана")
            }
            toast.emit("Города не загружены для ${errors.joinToString()}")
        }
    }

    private suspend fun handleCitiesState(bundle: CitiesRequestBundle) {
        val currentCities = cities.value
        cities.emit(
                currentCities.copy(
                        data = if (!bundle.isLoading && bundle.hasAnyData) {
                            listOfNotNull(
                                    bundle.ruCities.getDataOrNull(),
                                    bundle.byCities.getDataOrNull(),
                                    bundle.kzCities.getDataOrNull()
                            ).flatten()
                        } else {
                            currentCities.data
                        },
                        load = when {
                            bundle.isLoading -> PlaceholderState.MainLoading
                            bundle.hasAnyData -> PlaceholderState.None
                            bundle.allNoInternet -> PlaceholderState.NoInternet
                            else -> PlaceholderState.Error
                        }
                )
        )
    }
}

interface CitiesViewModel {
    val cities: StateFlow<RequestUi<List<City>>>
    val toast: SharedFlow<String>

    fun retry()
}