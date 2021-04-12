package ru.surfstudio.standard.f_splash

import android.content.Intent
import ru.surfstudio.android.navigation.route.activity.ActivityRoute
import ru.surfstudio.android.notification.ui.notification.strategies.PushHandleStrategy

/**
 * Маршрут сплеша
 */
class SplashRoute(val pushHandleStrategy: PushHandleStrategy<*>? = null) : ActivityRoute() {

    constructor(intent: Intent): this()

    override fun getScreenClassPath(): String {
        return "ru.surfstudio.standard.f_splash.SplashActivityView"
    }
}