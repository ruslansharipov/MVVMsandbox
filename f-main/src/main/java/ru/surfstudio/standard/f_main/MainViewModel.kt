package ru.surfstudio.standard.f_main

import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import ru.surfstudio.standard.ui.mvvm.view_model.BaseViewModel
import javax.inject.Inject

internal class MainViewModel @Inject constructor(
        override val navCommandExecutor: AppCommandExecutor
) : BaseViewModel(), IMainViewModel

internal interface IMainViewModel