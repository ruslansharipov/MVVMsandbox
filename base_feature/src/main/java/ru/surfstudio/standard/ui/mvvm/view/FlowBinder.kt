package ru.surfstudio.standard.ui.mvvm.view

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Интерфейс, предоставляющий удобные методы для подписки на флоу и каналы [Flow.bindTo] и [Channel.bindTo]
 * Подписки совершаются в рамках [coroutineScope] и [coroutineContext].
 * Классы-наследники должны очищать подписки в соответствющих методах жизненного цикла.
 *
 * Переопределение [coroutineContext] может потребоваться например в виджетах, чтобы у каждого виджета
 * был свой контекст, при помощи которого можно было бы отписываться от ранее созданых подписок этого
 * виджета, при этом не отменяя остальных подписок, созданных в родительском [coroutineScope].
 * Либо если для создания всех подписок требуется какой-то особых контекст.
 */
interface FlowBinder {

    val coroutineScope: CoroutineScope
    val coroutineContext: CoroutineContext get() = EmptyCoroutineContext

    /**
     * Общий метод, используемый для подписок на флоу и каналы
     */
    fun <T> subscribe(flow: Flow<T>, action: suspend (T) -> Unit): Job {
        return coroutineScope.launch(coroutineContext) {
            flow.collect { data: T ->
                action(data)
            }
        }
    }

    /**
     * Подписка на флоу
     *
     * @param action функция вызываемая на каждый эмит данных
     * @return [Job] позволяющий контролировать флоу и отписываться при необходимости
     */
    fun <T> Flow<T>.bindTo(action: suspend (T) -> Unit): Job {
        return subscribe(this, action)
    }

    /**
     * Подписка на флоу
     *
     * @param action функция вызываемая на каждый эмит данных
     * @return [Job] позволяющий контролировать флоу и отписываться при необходимости
     */
    fun <T> Channel<T>.bindTo(action: suspend (T) -> Unit): Job {
        return subscribe(this.receiveAsFlow(), action)
    }
}