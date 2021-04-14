package ru.surfstudio.standard.ui.navigation

import ru.surfstudio.android.navigation.route.fragment.FragmentRoute

class MainBarRoute : FragmentRoute() {

    override fun getScreenClassPath(): String {
        return "ru.surfstudio.standard.f_main.bar.MainBarFragmentView"
    }
}
