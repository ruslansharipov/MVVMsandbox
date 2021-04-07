package ru.surfstudio.android.mvvmsandbox.feature.cities

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

    /**
     * В этом методе три запроса оборачиваются в requestFlow - аналог нашего Observable.asRequest
     * который эмитит Request.Loading перед началом запроса, Request.Success если данные получены и
     * Request.Error если возникла ошибка.
     *
     * Данные о состоянии трех запросов оборачиваются в CitiesRequestBundle, который содержит методы
     * для отслеживания состояния запросов.
     *
     * Этот бандл обрабатывается в следующих методах
     * 1. handleCitiesError - здесь мы проверяем, что все три запроса завершены. И если данные в
     * каком-то из них были получены, но также в каком-то из запросов была получена ошибка - мы эту
     * ошибку вывозим в тост
     * 2. handleCitiesState - в этом методе мы трансформируем данные из бандла в RequestUi,
     * комбинируя списки городов, которые пришли и преобразуя состояния запросов в PlaceholderState
     */
    private fun loadCities() {
        viewModelScope.launch {
            combine(
                    requestFlow {
                        delay(2000) // иммитируем долгую загрузку
                        citiesInteractor.getCities("RU")
                    },
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