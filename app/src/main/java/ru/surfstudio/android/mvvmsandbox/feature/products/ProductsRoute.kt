package ru.surfstudio.android.mvvmsandbox.feature.products

import ru.surfstudio.android.navigation.route.fragment.FragmentRoute

class ProductsRoute: FragmentRoute() {

    override fun getScreenClassPath(): String {
        return "ru.surfstudio.android.mvvmsandbox.feature.products.ProductsFragmentView"
    }
}