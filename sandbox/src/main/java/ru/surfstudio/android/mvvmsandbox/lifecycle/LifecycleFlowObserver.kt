package ru.surfstudio.android.mvvmsandbox.lifecycle

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope

/**
 * Интерфейс для использования в активити и фрагментах, так как они имплементят [LifecycleOwner]
 */
interface LifecycleFlowObserver : FlowObserver, LifecycleOwner {
    override val coroutineScope: LifecycleCoroutineScope
        get() = lifecycle.coroutineScope
}