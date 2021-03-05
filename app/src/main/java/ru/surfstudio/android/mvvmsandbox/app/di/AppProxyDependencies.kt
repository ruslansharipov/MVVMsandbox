package ru.surfstudio.android.mvvmsandbox.app.di

import android.content.Context
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import ru.surfstudio.android.navigation.provider.ActivityNavigationProvider

/**
 * Интерфейс, объединяющий в себе все зависимости в скоупе [PerApplication]
 * Следует использовать в компоненте Application и других компонентах более высоких уровней,
 * зависящих от него.
 */
interface AppProxyDependencies {
    fun context(): Context

    fun commandExecutor(): AppCommandExecutor
    fun activityNavigationProvider(): ActivityNavigationProvider
}
