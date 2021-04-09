package ru.surfstudio.android.mvvmsandbox.feature.products

import ru.surfstudio.android.navigation.route.dialog.DialogRoute

class ProductsRoute: DialogRoute() {

    override fun getScreenClassPath(): String {
        return "ru.surfstudio.android.mvvmsandbox.feature.products.ProductsFragmentView"
    }
}