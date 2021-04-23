package ru.surfstudio.standard.ui.navigation

import android.content.Intent
import ru.surfstudio.android.navigation.route.activity.ActivityRoute

/**
 * Маршрут сплеша
 */
class SplashRoute() : ActivityRoute() {

    constructor(intent: Intent): this()

    override fun getScreenClassPath(): String {
        return "ru.surfstudio.standard.f_splash.SplashActivityView"
    }
}