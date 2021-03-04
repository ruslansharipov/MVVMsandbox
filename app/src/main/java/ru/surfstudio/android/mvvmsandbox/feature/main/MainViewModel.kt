package ru.surfstudio.android.mvvmsandbox.feature.main

import androidx.lifecycle.ViewModel
import ru.surfstudio.android.mvvmsandbox.feature.favorites.FavoritesRoute
import ru.surfstudio.android.navigation.command.fragment.Replace
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val appCommandExecutor: AppCommandExecutor
): ViewModel() {

    init {
        appCommandExecutor.execute(Replace(FavoritesRoute()))
    }

}
