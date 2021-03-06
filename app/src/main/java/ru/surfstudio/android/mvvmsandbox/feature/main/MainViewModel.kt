package ru.surfstudio.android.mvvmsandbox.feature.main

import androidx.lifecycle.ViewModel
import ru.surfstudio.android.mvvmsandbox.feature.products.ProductsRoute
import ru.surfstudio.android.mvvmsandbox.navigation.view.model.NavigationViewModel
import ru.surfstudio.android.navigation.command.activity.Finish
import ru.surfstudio.android.navigation.command.dialog.Show
import ru.surfstudio.android.navigation.command.fragment.RemoveLast
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import ru.surfstudio.android.navigation.provider.ActivityNavigationProvider
import javax.inject.Inject

class MainViewModel @Inject constructor(
    override val navCommandExecutor: AppCommandExecutor,
    private val activityNavigationProvider: ActivityNavigationProvider,
    private val route: MainScreenRoute
): ViewModel(), IMainViewModel, NavigationViewModel {

    init {
        Show(ProductsRoute()).execute()
    }

    override fun onBackPress() {
        val fragmentNavigator = activityNavigationProvider.provide()
                .fragmentNavigationProvider
                .provide(route.getId())
                .fragmentNavigator
        if (fragmentNavigator.backStackEntryCount > 1){
            RemoveLast().execute()
        } else {
            Finish().execute()
        }
    }
}


interface IMainViewModel {
    fun onBackPress()
}