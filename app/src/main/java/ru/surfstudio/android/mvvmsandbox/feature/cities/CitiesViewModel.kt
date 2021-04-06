package ru.surfstudio.android.mvvmsandbox.feature.cities

import androidx.lifecycle.ViewModel
import ru.surfstudio.android.mvvmsandbox.feature.di.ScreenScope
import ru.surfstudio.android.mvvmsandbox.interaction.cities.CitiesInteractor
import ru.surfstudio.android.mvvmsandbox.navigation.view.model.NavigationViewModel
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import javax.inject.Inject

@ScreenScope
class CitiesViewModelImpl @Inject constructor(
    private val citiesInteractor: CitiesInteractor,
    override val navCommandExecutor: AppCommandExecutor
) : ViewModel(), CitiesViewModel, NavigationViewModel {


}

interface CitiesViewModel {

}