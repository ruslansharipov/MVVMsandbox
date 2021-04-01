package ru.surfstudio.android.mvvmsandbox.feature.map

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.surfstudio.android.mvvmsandbox.feature.di.ScreenScope
import javax.inject.Inject

@ScreenScope
class MapViewModelImpl @Inject constructor(
) : ViewModel(), MapViewModel {

    override val permissionState = MutableStateFlow(PermissionStatus.DENIED)

    override fun permissionUpdated(isGranted: Boolean) {
        val status = if (isGranted) {
            PermissionStatus.GRANTED
        } else {
            PermissionStatus.DENIED
        }
        permissionState.tryEmit(status)
    }

}

interface MapViewModel {

    val permissionState: StateFlow<PermissionStatus>

    fun permissionUpdated(isGranted: Boolean)
}