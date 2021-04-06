package ru.surfstudio.android.mvvmsandbox.feature.cities

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
import java.lang.StringBuilder
import javax.inject.Inject

@ScreenScope
class CitiesViewModelImpl @Inject constructor(
        private val citiesInteractor: CitiesInteractor,
        override val navCommandExecutor: AppCommandExecutor
) : ViewModel(), CitiesViewModel, NavigationViewModel {

    override val cities = MutableStateFlow<RequestUi<List<City>>>(RequestUi())
    override val snack = MutableSharedFlow<String>()

    init {
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
                            snack.emit("Города не загружены для ${errors.joinToString()}")
                        }
                    }
        }
    }
}

interface CitiesViewModel {
    val cities: StateFlow<RequestUi<List<City>>>
    val snack: SharedFlow<String>
}