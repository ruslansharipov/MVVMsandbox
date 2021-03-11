package ru.surfstudio.android.mvvmsandbox.feature.products

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.surfstudio.android.core.mvi.ui.mapper.RequestMapper
import ru.surfstudio.android.core.mvp.binding.rx.request.Request
import ru.surfstudio.android.core.mvp.binding.rx.request.data.RequestUi
import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList
import ru.surfstudio.android.mvvmsandbox.domain.Product
import ru.surfstudio.android.mvvmsandbox.interaction.catalog.CatalogInteractor
import ru.surfstudio.android.mvvmsandbox.util.RequestMappers
import ru.surfstudio.android.mvvmsandbox.network.requestFlow
import ru.surfstudio.android.mvvmsandbox.pagination.PaginationBundle
import javax.inject.Inject

class ProductsViewModel @Inject constructor(
    private val catalogInteractor: CatalogInteractor
) : ViewModel(), IProductsViewModel {

    override val products = MutableLiveData<RequestUi<PaginationBundle<Product>>>(RequestUi())

    init {
        Log.d("ProductsViewModel", "init: ${this.hashCode()}")
        loadProducts(page = 0)
    }

    override fun loadMode() {
        loadProducts(products.value?.data?.list?.nextPage ?: 0)
    }

    private fun loadProducts(page: Int) {
        viewModelScope.launch {
            requestFlow { catalogInteractor.getProducts(page) }
                .flowOn(Dispatchers.IO)
                .collect { request: Request<DataList<Product>> ->
                    products.value = RequestMapper.builder(request, products.value?.data)
                        .mapData(RequestMappers.data.pagination())
                        .mapLoading(RequestMappers.loading.simple())
                        .handleError { error, data, loading ->
                            // TODO handle error
                            true
                        }
                        .build()
                }
        }
    }

}

interface IProductsViewModel {
    val products: LiveData<RequestUi<PaginationBundle<Product>>>
    fun loadMode()
}