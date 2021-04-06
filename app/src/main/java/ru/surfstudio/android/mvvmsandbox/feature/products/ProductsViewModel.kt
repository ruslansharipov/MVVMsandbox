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
import ru.surfstudio.android.mvvmsandbox.util.requestFlow
import ru.surfstudio.android.mvvmsandbox.pagination.PaginationBundle
import javax.inject.Inject

class ProductsViewModel @Inject constructor(
    private val catalogInteractor: CatalogInteractor
) : ViewModel(), IProductsViewModel {

    override val products = MutableLiveData<RequestUi<PaginationBundle<Product>>>(RequestUi())
    override val toasts = MutableLiveData<String>()

    init {
        Log.d("ProductsViewModel", "init: ${this.hashCode()}")
        loadProducts(page = 1)
    }

    override fun loadMode() {
        loadProducts(products.value?.data?.list?.nextPage ?: 1)
    }

    override fun onFavoriteClick(product: Product) {
        viewModelScope.launch {
            requestFlow {
                if (product.addedToWishlist) {
                    catalogInteractor.removeFavorite(product.code)
                } else {
                    catalogInteractor.addFavorite(product.code)
                }
            }
                .flowOn(Dispatchers.IO)
                .collect { request: Request<Unit> ->
                    updateFavoriteStatus(request, product.code, !product.addedToWishlist)
                    reactOnFavoriteRequest(request, product.code, !product.addedToWishlist)
                }
        }
    }

    private fun updateFavoriteStatus(
        request: Request<Unit>,
        productCode: String,
        addedToWishList: Boolean
    ) {
        val currentValue = products.value
        products.value = when (request) {
            is Request.Loading -> currentValue?.copy(
                data = currentValue.data?.copy(
                    list = currentValue.data?.list?.changeProductWith(productCode) {
                        it.copy(addedToWishlist = addedToWishList)
                    }
                )
            )
            is Request.Error -> currentValue?.copy(
                data = currentValue.data?.copy(
                    list = currentValue.data?.list?.changeProductWith(productCode) {
                        it.copy(addedToWishlist = !addedToWishList)
                    }
                )
            )
            is Request.Success -> {
                products.value
            }
        }
    }

    private fun reactOnFavoriteRequest(
        request: Request<Unit>,
        productCode: String,
        addedToWishList: Boolean
    ) {
        when(request){
            is Request.Success -> {
                val message = if (addedToWishList) {
                    "Продукт $productCode добавлен в избранное"
                } else {
                    "Продукт $productCode удален из избранного"
                }
                toasts.postValue(message)
            }
            is Request.Error -> {
                val message = if (addedToWishList) {
                    "Ошибка добавления продукта $productCode в избранное"
                } else {
                    "Ошибка удаления продукта $productCode из избранного"
                }
                toasts.postValue(message)
            }
        }
    }

    private fun DataList<Product>.changeProductWith(
        code: String,
        transform: (Product) -> Product
    ): DataList<Product> {
        return this.transform { product: Product ->
            if (product.code == code) {
                transform(product)
            } else {
                product
            }
        }
    }

    private fun loadProducts(page: Int) {
        viewModelScope.launch {
            requestFlow { catalogInteractor.getProducts(page) }
                .flowOn(Dispatchers.IO)
                .collect { request: Request<DataList<Product>> ->
                    products.value = RequestMapper.builder(request, products.value?.data)
                        .mapData(RequestMappers.data.pagination())
                        .mapLoading(RequestMappers.loading.simple())
                        .build()
                }
        }
    }
}

interface IProductsViewModel {
    val products: LiveData<RequestUi<PaginationBundle<Product>>>
    val toasts: LiveData<String>
    fun loadMode()
    fun onFavoriteClick(product: Product)
}