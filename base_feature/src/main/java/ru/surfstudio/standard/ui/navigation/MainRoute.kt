package ru.surfstudio.standard.ui.navigation

import android.content.Intent
import ru.surfstudio.android.navigation.route.activity.ActivityRoute

class MainRoute() : ActivityRoute() {

    constructor(intent: Intent) : this()

    override fun getScreenClassPath(): String {
        return "ru.surfstudio.standard.f_main.container.MainActivityView"
    }
}