package ru.surfstudio.android.mvvmsandbox.configurator.component

import ru.surfstudio.android.mvvmsandbox.configurator.InjectionTarget

/**
 * Компонент экрана
 */
interface ScreenComponent<T : InjectionTarget> {
    fun inject(target: T)
}