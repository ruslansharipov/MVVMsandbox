package ru.surfstudio.standard.base.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

/**
 * Флоу, которое не эмитит никакие данные и нужно просто для уведомления о том,
 * что асинхронная работа выполнена
 */
typealias CompletableFlow = Flow<Unit>

/**
 * @param action экшен для асинхронного выполнения
 * @return [Flow] который эмитит Unit после выполнения [action]
 */
fun completableFlow(action: suspend () -> Unit): CompletableFlow {
    return flow {
        action()
        emit()
    }
}

/**
 * Утилитарная функция для эмита Unit в [CompletableFlow]
 */
suspend fun FlowCollector<Unit>.emit() = emit(Unit)