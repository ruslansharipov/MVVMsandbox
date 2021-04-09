package ru.surfstudio.android.mvvmsandbox.configurator

import ru.surfstudio.android.mvvmsandbox.configurator.component.BindableScreenComponent
import ru.surfstudio.android.mvvmsandbox.configurator.component.ScreenComponent

/**
 * Сущность, предоставляющая зависимости
 */
interface Configurator {

    /**
     * @return компонент для разрешения зависимостей
     */
    fun createComponent(): ScreenComponent<out InjectionTarget>

    /**
     * Конфигурирует передаваемый [target]
     * Создает компонент экрана и запрашивает зависимости, вызывает принудительную инициализацию
     * сущностей если это требуется, подробнее - см [BindableScreenComponent]
     */
    fun configure(target: InjectionTarget) {
        val component = createComponent()
        (component as ScreenComponent<InjectionTarget>).inject(target)
        if (component is BindableScreenComponent) {
            component.requestInjection()
        }
    }
}