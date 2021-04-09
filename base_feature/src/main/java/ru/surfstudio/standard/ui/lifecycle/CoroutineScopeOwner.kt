package ru.surfstudio.standard.ui.lifecycle

import androidx.lifecycle.LifecycleCoroutineScope

/**
 * Интерфейс для сущности, у которой есть свой скоуп
 */
interface CoroutineScopeOwner {
    val coroutineScope: LifecycleCoroutineScope
}