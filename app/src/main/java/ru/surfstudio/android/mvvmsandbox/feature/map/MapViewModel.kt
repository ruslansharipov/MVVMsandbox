package ru.surfstudio.android.mvvmsandbox.feature.map

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.surfstudio.android.mvvmsandbox.feature.di.ScreenScope
import ru.surfstudio.android.mvvmsandbox.feature.dialog.PermissionDialogRoute
import ru.surfstudio.android.mvvmsandbox.navigation.view.model.NavigationViewModel
import ru.surfstudio.android.navigation.command.dialog.Show
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import ru.surfstudio.android.navigation.observer.ScreenResultObserver
import javax.inject.Inject

@ScreenScope
class MapViewModelImpl @Inject constructor(
    private val screenResultObserver: ScreenResultObserver,
    override val navCommandExecutor: AppCommandExecutor
) : ViewModel(), MapViewModel, NavigationViewModel {

    override val permissionState = MutableStateFlow(PermissionStatus.DENIED)

    override fun permissionUpdated(isGranted: Boolean) {
        val status = if (isGranted) {
            PermissionStatus.GRANTED
        } else {
            PermissionStatus.DENIED
        }
        permissionState.tryEmit(status)
        if (!isGranted) {
            Show(PermissionDialogRoute()).execute()
        }
    }

}

interface MapViewModel {

    val permissionState: StateFlow<PermissionStatus>

    fun permissionUpdated(isGranted: Boolean)
}