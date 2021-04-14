package ru.surfstudio.standard.application.app.callbacks.log

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import ru.surfstudio.android.logger.Logger
import ru.surfstudio.standard.ui.configurator.HasName

/**
 * Наблюдатель жизненного цикла, логирующий изменения состояний
 */
class LogLifecycleObserver: LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        val name: String = if (source is HasName) {
            source.name
        } else {
            source::class.simpleName
        } ?: ""
        Logger.d("$LIFECYCLE | $name | $event")
    }

    companion object {
        const val LIFECYCLE = "LIFECYCLE"
    }
}