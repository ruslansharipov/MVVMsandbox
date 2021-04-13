package ru.surfstudio.standard.f_main.widget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import ru.surfstudio.standard.f_main.widget.data.ProductUi
import ru.surfstudio.standard.ui.mvvm.view_model.NavigationViewModel
import javax.inject.Inject

/**
 * Вьюмодель виджета с состоянием [productState] и командами [toasts]
 */
@PerScreen
class ProductViewModelImpl @Inject constructor(
        initialProduct: ProductUi,
        override val navCommandExecutor: AppCommandExecutor
) : ViewModel(), ProductViewModel, NavigationViewModel {

    override val productState = MutableStateFlow(initialProduct)
    override val toasts = MutableSharedFlow<String>()

    override fun onFavoriteClick() {
        viewModelScope.launch {
            val product = productState.value
            val newPrice = product.price + 1
            productState.emit(product.copy(price = newPrice))
            toasts.emit("Price increased! New price: $newPrice")
        }
    }
}

interface ProductViewModel {
    val productState: StateFlow<ProductUi>
    val toasts: SharedFlow<String>
    fun onFavoriteClick()
}