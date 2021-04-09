package ru.surfstudio.android.mvvmsandbox.feature.cities

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class LifecycleFragment : Fragment(), LifecycleHubOwner {

    override val lifecycleStateHub = MutableSharedFlow<Lifecycle.Event>()

    private val lifecycleObserver = LifecycleObserver(lifecycleStateHub)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(lifecycleObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(lifecycleObserver)
    }
}

interface LifecycleHubOwner {
    val lifecycleStateHub: SharedFlow<Lifecycle.Event>
}

class LifecycleObserver(
        private val lifecycleEventHub: MutableSharedFlow<Lifecycle.Event>
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        lifecycleEventHub.tryEmit(Lifecycle.Event.ON_CREATE)
    }

    override fun onStart(owner: LifecycleOwner) {
        lifecycleEventHub.tryEmit(Lifecycle.Event.ON_START)
    }

    override fun onResume(owner: LifecycleOwner) {
        lifecycleEventHub.tryEmit(Lifecycle.Event.ON_RESUME)
    }

    override fun onPause(owner: LifecycleOwner) {
        lifecycleEventHub.tryEmit(Lifecycle.Event.ON_PAUSE)
    }

    override fun onStop(owner: LifecycleOwner) {
        lifecycleEventHub.tryEmit(Lifecycle.Event.ON_STOP)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        lifecycleEventHub.tryEmit(Lifecycle.Event.ON_DESTROY)
    }
}