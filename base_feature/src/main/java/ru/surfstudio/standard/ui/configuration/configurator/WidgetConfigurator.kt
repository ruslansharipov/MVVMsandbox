package ru.surfstudio.standard.ui.configuration.configurator

import android.app.Activity
import android.content.Context
import android.view.ContextThemeWrapper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.lifecycleScope
import ru.surfstudio.standard.ui.activity.di.ActivityComponent
import ru.surfstudio.standard.ui.configuration.InjectionTarget
import ru.surfstudio.standard.ui.configuration.component.ScreenComponent

/**
 * Конфигуратор виджета
 */
abstract class WidgetConfigurator<T : Any> : Configurator {

    abstract fun createWidgetComponent(
            activityComponent: ActivityComponent,
            viewModelStore: ViewModelStore,
            lifecycleScope: LifecycleCoroutineScope,
            args: T
    ): ScreenComponent<out InjectionTarget>

    /**
     * Конфигурирует виджет
     */
    fun <W> configure(widget: W, args: T) where W : InjectionTarget, W : View {
        val activity = widget.context.findActivity()
        requireNotNull(activity)

        val (viewModelStore, lifecycleScope) = try {
            val fragment = widget.findFragment<Fragment>()
            fragment.viewModelStore to fragment.lifecycleScope
        } catch (e: Throwable) {
            val appCompatActivity = activity as AppCompatActivity
            appCompatActivity.viewModelStore to appCompatActivity.lifecycleScope
        }
        val activityComponent = createActivityComponent(activity)
        val screenComponent = createWidgetComponent(activityComponent, viewModelStore, lifecycleScope, args)
        resolveDependencies(widget, screenComponent)
    }
}

/**
 * Рекурсивный поиск активити и использованием контекста
 */
fun Context.findActivity(): Activity? {
    return when (this) {
        is Activity -> this
        is ContextThemeWrapper -> baseContext.findActivity()
        else -> null
    }
}