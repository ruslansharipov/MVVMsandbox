package ru.surfstudio.standard.ui.configuration.configurator

import androidx.lifecycle.ViewModelStore
import ru.surfstudio.standard.ui.activity.di.ActivityComponent
import ru.surfstudio.standard.ui.configuration.InjectionTarget
import ru.surfstudio.standard.ui.configuration.component.ScreenComponent

/**
 * Конфигуратор экрана, создающий компонент экрана
 */
interface ScreenConfigurator<T : Any> : Configurator {

    /**
     * @return компонент для разрешения зависимостей
     */
    fun createComponent(
            activityComponent: ActivityComponent,
            viewModelStore: ViewModelStore,
            args: T
    ): ScreenComponent<out InjectionTarget>
}