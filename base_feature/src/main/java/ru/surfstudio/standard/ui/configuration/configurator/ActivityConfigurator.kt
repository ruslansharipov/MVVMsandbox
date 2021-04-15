package ru.surfstudio.standard.ui.configuration.configurator

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import ru.surfstudio.standard.ui.activity.di.ActivityComponent
import ru.surfstudio.standard.ui.configuration.InjectionTarget
import ru.surfstudio.standard.ui.configuration.component.ScreenComponent

/**
 * Конфигуратор для активити
 */
abstract class ActivityConfigurator: ScreenConfigurator<Intent> {

    abstract override fun createComponent(activityComponent: ActivityComponent, viewModelStore: ViewModelStore, args: Intent): ScreenComponent<out InjectionTarget>

    /**
     * Конфигурирует передаваемую активити.
     * Создает [ActivityComponent], компонент экрана и запрашивает разрешение зависимостей
     */
    fun <A> configure(activity: A) where A: Activity, A: ViewModelStoreOwner, A: InjectionTarget {
        val activityComponent = createActivityComponent(activity)
        val screenComponent = createComponent(activityComponent, activity.viewModelStore, activity.intent)
        resolveDependencies(activity, screenComponent)
    }
}