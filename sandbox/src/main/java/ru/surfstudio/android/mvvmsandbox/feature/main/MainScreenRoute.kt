package ru.surfstudio.android.mvvmsandbox.feature.main

import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import ru.surfstudio.android.navigation.route.Route
import ru.surfstudio.android.navigation.route.activity.ActivityRoute

class MainScreenRoute(val screenId: String): ActivityRoute() {

    constructor(intent: Intent): this(intent.getStringExtra(Route.EXTRA_FIRST) ?: "")

    override fun prepareData(): Bundle {
        return bundleOf(Route.EXTRA_FIRST to screenId)
    }

    override fun getId(): String {
        return super.getId() + screenId
    }

    override fun getScreenClassPath(): String {
        return "ru.surfstudio.android.mvvmsandbox.feature.main.MainActivity"
    }
}