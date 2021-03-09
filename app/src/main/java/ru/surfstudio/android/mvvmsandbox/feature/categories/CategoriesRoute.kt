package ru.surfstudio.android.mvvmsandbox.feature.categories

import android.os.Bundle
import androidx.core.os.bundleOf
import ru.surfstudio.android.mvvmsandbox.domain.Category
import ru.surfstudio.android.navigation.route.Route
import ru.surfstudio.android.navigation.route.fragment.FragmentRoute

class CategoriesRoute(
        val subcategories: List<Category> = emptyList()
) : FragmentRoute() {

    constructor(bundle: Bundle): this(bundle.getSerializable(Route.EXTRA_FIRST) as List<Category>)

    override fun prepareData(): Bundle {
        return bundleOf(Route.EXTRA_FIRST to subcategories)
    }

    override fun getScreenClassPath(): String {
        return "ru.surfstudio.android.mvvmsandbox.feature.categories.CategoriesFragmentView"
    }
}