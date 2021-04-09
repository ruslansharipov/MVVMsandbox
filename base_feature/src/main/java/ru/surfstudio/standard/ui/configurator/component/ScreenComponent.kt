package ru.surfstudio.standard.ui.configurator.component

import ru.surfstudio.standard.ui.configurator.InjectionTarget

/**
 * Компонент экрана
 */
interface ScreenComponent<T : InjectionTarget> {
    fun inject(target: T)
}