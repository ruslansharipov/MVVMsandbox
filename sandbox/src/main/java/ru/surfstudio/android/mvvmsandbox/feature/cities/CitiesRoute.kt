package ru.surfstudio.android.mvvmsandbox.feature.cities

import ru.surfstudio.android.navigation.route.fragment.FragmentRoute

class CitiesRoute: FragmentRoute() {

    override fun getScreenClassPath(): String {
        return "ru.surfstudio.android.mvvmsandbox.feature.cities.CitiesFragmentView"
    }
}