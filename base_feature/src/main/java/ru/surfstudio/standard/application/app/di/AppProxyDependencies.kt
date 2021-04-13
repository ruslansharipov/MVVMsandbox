package ru.surfstudio.standard.application.app.di

import android.content.Context
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import ru.surfstudio.android.navigation.observer.ScreenResultObserver
import ru.surfstudio.standard.i_initialization.InitializeAppInteractor
import ru.surfstudio.standard.i_network.network.interactor.ConnectionChecker

/**
 * Интерфейс, объединяющий в себе все зависимости в скоупе [PerApplication]
 * Следует использовать в компоненте Application и других компонентах более высоких уровней,
 * зависящих от него.
 */
interface AppProxyDependencies {
    fun context(): Context

    fun screenResultObserver(): ScreenResultObserver
    fun commandExecutor(): AppCommandExecutor
    fun connectionChecker(): ConnectionChecker

    fun initializeAppInteractor(): InitializeAppInteractor

}
