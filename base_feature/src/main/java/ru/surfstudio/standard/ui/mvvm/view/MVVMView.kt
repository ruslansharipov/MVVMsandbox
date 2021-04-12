package ru.surfstudio.standard.ui.mvvm.view

import ru.surfstudio.standard.ui.configurator.HasConfigurator
import ru.surfstudio.standard.ui.configurator.InjectionTarget
import ru.surfstudio.standard.ui.lifecycle.FlowObserver
import ru.surfstudio.standard.ui.lifecycle.LifecycleFlowObserver

/**
 * Интерфейс вью для работы с вьюмоделью, расширяющий необходимые интерфейсы для организации
 * внедрения зависимостей и подписок на потоки данных
 */
interface MVVMView : LifecycleFlowObserver,
        FlowObserver,
        HasConfigurator,
        InjectionTarget