package ru.surfstudio.standard.ui.configuration.configurator

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelStore
import ru.surfstudio.standard.ui.activity.di.ActivityComponent
import ru.surfstudio.standard.ui.configuration.InjectionTarget
import ru.surfstudio.standard.ui.configuration.component.ScreenComponent

/**
 * Конфигуратор фрагментов
 */
abstract class FragmentConfigurator: ScreenConfigurator<Bundle> {

    abstract override fun createComponent(
            activityComponent: ActivityComponent,
            viewModelStore: ViewModelStore,
            args: Bundle
    ): ScreenComponent<out InjectionTarget>

    /**
     * Конфигурирует передаваемый фрагмент.
     * Создает [ActivityComponent], компонент экрана и запрашивает разрешение зависимостей
     */
    fun <F> configure(fragment: F) where F : Fragment, F: InjectionTarget {
        val activityComponent = createActivityComponent(fragment.activity as Activity)
        val screenComponent = createComponent(activityComponent, fragment.viewModelStore, fragment.arguments ?: Bundle.EMPTY)
        resolveDependencies(fragment, screenComponent)
    }
}