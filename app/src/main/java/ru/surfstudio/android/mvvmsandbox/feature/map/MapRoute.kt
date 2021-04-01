package ru.surfstudio.android.mvvmsandbox.feature.map

import ru.surfstudio.android.navigation.route.fragment.FragmentRoute

class MapRoute: FragmentRoute() {

    override fun getScreenClassPath(): String {
        return "ru.surfstudio.android.mvvmsandbox.feature.map.MapFragmentView"
    }
}