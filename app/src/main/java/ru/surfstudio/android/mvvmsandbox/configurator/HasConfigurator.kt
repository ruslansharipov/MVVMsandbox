package ru.surfstudio.android.mvvmsandbox.configurator

interface HasConfigurator {

    fun createConfigurator(): Configurator
}

interface Configurator {

    fun createComponent(): ScreenComponent<out InjectionTarget>

    fun configure(target: InjectionTarget) {
        val component = createComponent()
        (component as ScreenComponent<InjectionTarget>).inject(target)
        if (component is BindableScreenComponent){
            component.requestInjection()
        }
    }
}

/**
 * Интерфейс для сущности, которой требуется внедрение зависимостей
 */
interface InjectionTarget

/**
 * Компонент экрана
 */
interface ScreenComponent<T: InjectionTarget> {
    fun inject(target: T)
}

/**
 * Компонент для экранов с биндингом.
 * Вызов [requestInjection] проинциализирует все зависимости c типом [Any] в даггер модуле.
 * Пример: используется для инициализации презентера, без явного указания @Inject на поле.
 */
interface BindableScreenComponent<T: InjectionTarget>: ScreenComponent<T> {

    fun requestInjection(): Any
}