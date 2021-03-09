package ru.surfstudio.android.mvvmsandbox.feature.categories

import android.os.Bundle
import androidx.core.os.bundleOf
import ru.surfstudio.android.navigation.route.Route
import ru.surfstudio.android.navigation.route.fragment.FragmentRoute

class CategoriesRoute(
        val someArgs: String
) : FragmentRoute() {

    constructor(bundle: Bundle): this(bundle.getString(Route.EXTRA_FIRST, ""))

    override fun prepareData(): Bundle {
        return bundleOf(Route.EXTRA_FIRST to someArgs)
    }

    override fun getScreenClassPath(): String {
        return "ru.surfstudio.android.mvvmsandbox.feature.favorites.FavoritesFragmentView"
    }
}