package ru.surfstudio.standard.f_main.container

import ru.surfstudio.android.navigation.command.fragment.Replace
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import ru.surfstudio.standard.ui.animation.FadeAnimations
import ru.surfstudio.standard.ui.mvvm.view_model.BaseViewModel
import ru.surfstudio.standard.ui.navigation.MainBarRoute
import javax.inject.Inject

internal class MainViewModel @Inject constructor(
        override val navCommandExecutor: AppCommandExecutor
) : BaseViewModel(), IMainViewModel {

    init {
        Replace(
                route = MainBarRoute(),
                animations = FadeAnimations
        ).execute()
    }

}

internal interface IMainViewModel