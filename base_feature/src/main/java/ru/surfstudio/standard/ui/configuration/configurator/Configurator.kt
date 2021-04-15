package ru.surfstudio.standard.ui.configuration.configurator

import android.app.Activity
import ru.surfstudio.standard.application.app.App
import ru.surfstudio.standard.ui.activity.di.ActivityComponent
import ru.surfstudio.standard.ui.activity.di.ActivityModule
import ru.surfstudio.standard.ui.activity.di.DaggerActivityComponent
import ru.surfstudio.standard.ui.configuration.InjectionTarget
import ru.surfstudio.standard.ui.configuration.component.BindableScreenComponent
import ru.surfstudio.standard.ui.configuration.component.ScreenComponent

/**
 * Сущность, предоставляющая зависимости
 */
interface Configurator {

    /**
     * Создает [ActivityComponent]
     */
    fun createActivityComponent(activity: Activity): ActivityComponent {
        return DaggerActivityComponent.builder()
                .appComponent((activity.application as App).appComponent)
                .activityModule(ActivityModule())
                .build()
    }

    /**
     * Запрашивает зависимости, вызывает принудительную инициализацию
     * сущностей если это требуется, подробнее - см [BindableScreenComponent]
     */
    fun resolveDependencies(target: InjectionTarget, screenComponent: ScreenComponent<out InjectionTarget>) {
        (screenComponent as ScreenComponent<InjectionTarget>).inject(target)
        if (screenComponent is BindableScreenComponent) {
            screenComponent.requestInjection()
        }
    }
}