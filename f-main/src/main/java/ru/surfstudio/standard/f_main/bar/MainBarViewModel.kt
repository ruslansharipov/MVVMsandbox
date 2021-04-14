package ru.surfstudio.standard.f_main.bar

import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import ru.surfstudio.standard.ui.mvvm.view_model.BaseViewModel

internal class MainBarViewModel(
        override val navCommandExecutor: AppCommandExecutor
) : BaseViewModel(), IMainBarViewModel {

}

internal interface IMainBarViewModel
