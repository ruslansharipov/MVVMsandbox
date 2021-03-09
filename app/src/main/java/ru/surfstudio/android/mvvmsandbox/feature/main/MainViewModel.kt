package ru.surfstudio.android.mvvmsandbox.feature.main

import androidx.lifecycle.ViewModel
import ru.surfstudio.android.mvvmsandbox.feature.categories.CategoriesRoute
import ru.surfstudio.android.mvvmsandbox.navigation.view.model.NavigationViewModel
import ru.surfstudio.android.navigation.command.fragment.Replace
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import javax.inject.Inject

class MainViewModel @Inject constructor(
        override val navCommandExecutor: AppCommandExecutor
): ViewModel(), IMainViewModel, NavigationViewModel {

    init {
        Replace(CategoriesRoute("Some important arguments")).execute()
    }

}


interface IMainViewModel