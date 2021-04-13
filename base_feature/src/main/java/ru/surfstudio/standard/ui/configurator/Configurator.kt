package ru.surfstudio.standard.ui.configurator

import android.app.Activity
import android.content.Context
import android.view.ContextThemeWrapper
import android.view.View
import androidx.fragment.app.Fragment
import ru.surfstudio.standard.application.app.App
import ru.surfstudio.standard.ui.activity.di.ActivityComponent
import ru.surfstudio.standard.ui.activity.di.ActivityModule
import ru.surfstudio.standard.ui.activity.di.DaggerActivityComponent
import ru.surfstudio.standard.ui.configurator.component.BindableScreenComponent
import ru.surfstudio.standard.ui.configurator.component.ScreenComponent

/**
 * Сущность, предоставляющая зависимости
 */
interface Configurator {

    /**
     * @return компонент для разрешения зависимостей
     */
    fun createComponent(activityComponent: ActivityComponent): ScreenComponent<out InjectionTarget>

    /**
     * Конфигурирует передаваемый [target]
     * Создает компонент экрана и запрашивает зависимости, вызывает принудительную инициализацию
     * сущностей если это требуется, подробнее - см [BindableScreenComponent]
     */
    fun configure(target: InjectionTarget) {
        val activityComponent = createActivityComponent(target)
        val screenComponent = createComponent(activityComponent)
        (screenComponent as ScreenComponent<InjectionTarget>).inject(target)
        if (screenComponent is BindableScreenComponent) {
            screenComponent.requestInjection()
        }
    }

    private fun createActivityComponent(target: InjectionTarget): ActivityComponent {
        val activity = when (target) {
            is Fragment -> target.activity
            is Activity -> target
            is View -> target.context.findActivity()
            else -> null
        }
        requireNotNull(activity)
        return DaggerActivityComponent.builder()
                .appComponent((activity.application as App).appComponent)
                .activityModule(ActivityModule())
                .build()
    }
}

/**
 * Рекурсивный поиск активити и использованием контекста
 */
fun Context.findActivity(): Activity? {
    return when(this){
        is Activity -> this
        is ContextThemeWrapper -> baseContext.findActivity()
        else -> null
    }
}