package ru.surfstudio.standard.ui.mvvm.view

import ru.surfstudio.standard.ui.configuration.HasName
import ru.surfstudio.standard.ui.configuration.InjectionTarget

/**
 * Интерфейс вью для работы с вьюмоделью, расширяющий необходимые интерфейсы для организации
 * внедрения зависимостей и подписок на потоки данных
 */
interface MVVMView : FlowBinder, InjectionTarget, HasName