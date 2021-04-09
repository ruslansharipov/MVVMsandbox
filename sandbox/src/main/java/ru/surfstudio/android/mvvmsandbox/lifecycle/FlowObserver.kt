package ru.surfstudio.android.mvvmsandbox.lifecycle

import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Интерфейс наблюдателя флоу
 */
interface FlowObserver : CoroutineScopeOwner {

    /**
     * Подписка на канал
     *
     * @param action функция вызываемая на каждый эмит данных
     * @return [Job] позволяющий контролировать флоу и отписываться при необходимости
     */
    fun <T> Channel<T>.bindTo(action: suspend (T) -> Unit): Job {
        return coroutineScope.launch {
            receiveAsFlow().collect(action)
        }
    }

    /**
     * Подписка на флоу
     *
     * @param action функция вызываемая на каждый эмит данных
     * @return [Job] позволяющий контролировать флоу и отписываться при необходимости
     */
    fun <T> Flow<T>.bindTo(action: suspend (T) -> Unit): Job {
        return coroutineScope.launch {
            collect(action)
        }
    }
}