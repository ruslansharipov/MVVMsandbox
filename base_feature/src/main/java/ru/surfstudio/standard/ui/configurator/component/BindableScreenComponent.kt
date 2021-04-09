package ru.surfstudio.standard.ui.configurator.component

import ru.surfstudio.standard.ui.configurator.InjectionTarget

/**
 * Компонент для экранов с биндингом/mvi/любыми другими сущностями, требующими каких-то
 * дополнительных действий с сущностями перед их инжектом.
 * Вызов [requestInjection] проинциализирует все зависимости c типом [Any] в даггер модуле.
 *
 * Пример: используется для ScreenBinder в mvi, который связывает reducer, stateHolder, middleware,
 * но сам ScreenBinder при этом никуда не инжектится, а из метода даггера возвращается Any.
 *
 * @Provides
 * @PerScreen
 * fun provideBinder(
 *         screenBinderDependency: ScreenBinderDependency,
 *         eventHub: ScreenEventHub<CartEvent>,
 *         reducer: CartReducer,
 *         stateHolder: CartStateHolder,
 *         middleware: CartMiddleware
 * ): Any = ScreenBinder(screenBinderDependency).apply {
 *     bind(eventHub, middleware, stateHolder, reducer)
 * }
 */
interface BindableScreenComponent<T : InjectionTarget> : ScreenComponent<T> {

    fun requestInjection(): Any
}