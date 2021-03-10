package ru.surfstudio.android.mvvmsandbox.feature.categories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.surfstudio.android.mvvmsandbox.domain.Category
import ru.surfstudio.android.mvvmsandbox.interaction.catalog.CatalogInteractor
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

    private val TAG = "ViewModel"

    init {
        Log.d(TAG, "CategoriesViewModel: init ${hashCode()}")
    }

    override val categories: MutableLiveData<Request<List<Category>>> = MutableLiveData()

    init {
        val subcategories = route.category?.children
        if (subcategories == null) {
            categories.value = Request.Loading()
            viewModelScope.launch {
                delay(1000)
                categories.value = request { catalogInteractor.getCategories() }
            }
        } else {
            categories.value = Request.Success(subcategories)
        }
    }

    override fun categoryClick(category: Category) {
        val children = category.children
        if (children.isNotEmpty()) {
            Replace(CategoriesRoute(category)).execute()
        }
    }

    override fun onCleared() {
        Log.d(TAG, "CategoriesViewModel: cleared ${hashCode()}")
    }
}

interface ICategoriesViewModel {

    val categories: LiveData<Request<List<Category>>>

    fun categoryClick(category: Category)
}