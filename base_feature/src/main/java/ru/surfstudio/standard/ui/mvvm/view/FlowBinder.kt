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
 * Подписки совершаются в рамках [coroutineScope].
 * Классы-наследники должны очищать подписки в соответствющих методах жизненного цикла.
 */
interface FlowBinder {

    val coroutineScope: CoroutineScope

    /**
     * Общий метод, используемый для подписок на флоу и каналы
     */
    fun <T> subscribe(flow: Flow<T>, context: CoroutineContext, action: suspend (T) -> Unit): Job {
        return coroutineScope.launch(context) {
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
    fun <T> Flow<T>.bindTo(context: CoroutineContext = EmptyCoroutineContext, action: suspend (T) -> Unit): Job {
        return subscribe(this, context, action)
    }

    /**
     * Подписка на флоу
     *
     * @param action функция вызываемая на каждый эмит данных
     * @return [Job] позволяющий контролировать флоу и отписываться при необходимости
     */
    fun <T> Channel<T>.bindTo(context: CoroutineContext = EmptyCoroutineContext, action: suspend (T) -> Unit): Job {
        return subscribe(this.receiveAsFlow(), context, action)
    }
}