package ru.surfstudio.android.mvvmsandbox.feature.favorites

import androidx.lifecycle.ViewModel
import ru.surfstudio.android.mvvmsandbox.navigation.view.model.NavigationViewModel
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
        override val navCommandExecutor: AppCommandExecutor
): ViewModel(), NavigationViewModel, IFavoritesViewModel {



}

interface IFavoritesViewModel {

}