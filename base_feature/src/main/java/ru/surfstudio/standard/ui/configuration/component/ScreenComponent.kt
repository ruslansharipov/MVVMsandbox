package ru.surfstudio.standard.ui.configuration.component

import ru.surfstudio.standard.ui.configuration.InjectionTarget

/**
 * Компонент экрана
 */
interface ScreenComponent<T : InjectionTarget> {
    fun inject(target: T)
}