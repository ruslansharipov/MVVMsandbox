package ru.surfstudio.android.mvvmsandbox.feature.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.surfstudio.android.mvvmsandbox.domain.Category
import ru.surfstudio.android.mvvmsandbox.interaction.CatalogInteractor
import ru.surfstudio.android.mvvmsandbox.navigation.view.model.NavigationViewModel
import ru.surfstudio.android.mvvmsandbox.network.Request
import ru.surfstudio.android.mvvmsandbox.network.request
import ru.surfstudio.android.navigation.command.fragment.Replace
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(
        override val navCommandExecutor: AppCommandExecutor,
        private val catalogInteractor: CatalogInteractor,
        private val route: CategoriesRoute
) : ViewModel(), NavigationViewModel, ICategoriesViewModel {

    override val categories: MutableLiveData<Request<List<Category>>> = MutableLiveData()

    init {
        val subcategories = route.subcategories
        if (subcategories.isEmpty()) {
            viewModelScope.launch {
                categories.value = request { catalogInteractor.getCategories() }
            }
        } else {
            categories.value = Request.Success(subcategories)
        }
    }

    override fun categoryClick(category: Category) {
        val children = category.children
        if (children.isNotEmpty()) {
            Replace(CategoriesRoute(children))
        }
    }
}

interface ICategoriesViewModel {

    val categories: LiveData<Request<List<Category>>>

    fun categoryClick(category: Category)
}