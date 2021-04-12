package ru.surfstudio.standard.ui.widget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.surfstudio.android.core.mvp.binding.rx.request.Request
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import ru.surfstudio.standard.ui.mvvm.view_model.NavigationViewModel
import javax.inject.Inject

@PerScreen
class ProductViewModelImpl @Inject constructor(
        initialProduct: Product,
        override val navCommandExecutor: AppCommandExecutor,
        private val catalogInteractor: CatalogInteractor
) : ViewModel(), ProductViewModel, NavigationViewModel {

    override val productState = MutableStateFlow(initialProduct)
    override val toasts = MutableSharedFlow<String>()

    override fun onFavoriteClick() {
        val product = productState.value
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
                        updateFavoriteStatus(request, !product.addedToWishlist)
                        reactOnFavoriteRequest(request, product.code, !product.addedToWishlist)
                    }
        }
    }

    private fun updateFavoriteStatus(
            request: Request<Unit>,
            addedToWishList: Boolean
    ) {
        val currentValue = productState.value

        productState.value = when (request) {
            is Request.Loading -> currentValue.copy(addedToWishlist = addedToWishList)
            is Request.Error -> currentValue.copy(addedToWishlist = !addedToWishList)
            is Request.Success -> currentValue
        }
    }

    private suspend fun reactOnFavoriteRequest(
            request: Request<Unit>,
            productCode: String,
            addedToWishList: Boolean
    ) {
        when (request) {
            is Request.Success -> {
                val message = if (addedToWishList) {
                    "Продукт $productCode добавлен в избранное"
                } else {
                    "Продукт $productCode удален из избранного"
                }
                toasts.emit(message)
            }
            is Request.Error -> {
                val message = if (addedToWishList) {
                    "Ошибка добавления продукта $productCode в избранное"
                } else {
                    "Ошибка удаления продукта $productCode из избранного"
                }
                toasts.emit(message)
            }
        }
    }
}

interface ProductViewModel {
    val productState: StateFlow<Product>
    val toasts: SharedFlow<String>
    fun onFavoriteClick()
}