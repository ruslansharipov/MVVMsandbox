package ru.surfstudio.android.mvvmsandbox.feature.categories

import androidx.lifecycle.ViewModel
import ru.surfstudio.android.mvvmsandbox.navigation.view.model.NavigationViewModel
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(
        override val navCommandExecutor: AppCommandExecutor
): ViewModel(), NavigationViewModel, ICategoriesViewModel {



}

interface ICategoriesViewModel {

}