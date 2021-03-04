package ru.surfstudio.android.mvvmsandbox.feature.favorites

import ru.surfstudio.android.navigation.route.fragment.FragmentRoute

class FavoritesRoute: FragmentRoute() {

    override fun getScreenClassPath(): String {
        return "ru.surfstudio.android.mvvmsandbox.feature.favorites.FavoritesFragmentView"
    }
}