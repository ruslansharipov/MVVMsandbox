package ru.surfstudio.android.mvvmsandbox.feature.dialog

import ru.surfstudio.android.navigation.observer.route.ResultRoute
import ru.surfstudio.android.navigation.route.dialog.DialogRoute

class PermissionDialogRoute: DialogRoute(), ResultRoute<SimpleResult> {

    override fun getScreenClassPath(): String {
        return "ru.surfstudio.android.mvvmsandbox.feature.dialog.PermissionDialogView"
    }
}
