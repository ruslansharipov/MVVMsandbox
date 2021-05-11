package ru.surfstudio.standard.f_main.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.coroutines.Job
import ru.surfstudio.android.logger.Logger
import ru.surfstudio.standard.f_main.R
import ru.surfstudio.standard.f_main.widget.data.ProductUi
import ru.surfstudio.standard.f_main.widget.di.ProductWidgetConfigurator
import ru.surfstudio.standard.ui.mvvm.view.MVVMView
import javax.inject.Inject

/**
 * Пример виджета с вьюмоделью
 */
class ProductWidget @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), MVVMView {

    @Inject
    override lateinit var coroutineScope: LifecycleCoroutineScope

    override val name: String = "ProductWidget"

    @Inject
    lateinit var viewModel: ProductViewModel

    private var productJob: Job? = null
    private var toastsJob: Job? = null

    private val priceTv: TextView

    init {
        View.inflate(context, R.layout.widget_product_view, this)

        priceTv = findViewById(R.id.product_price_tv)
        priceTv.setOnClickListener { viewModel.onFavoriteClick() }
    }

    private fun bindInternal() {
        productJob?.cancel()
        productJob = viewModel.productState.bindTo { product: ProductUi ->
            priceTv.text = product.price.toString()
        }
        toastsJob?.cancel()
        toastsJob = viewModel.toasts.bindTo { toast ->
//            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * При привязке данных инициализируем конфигуратор и конфигурируем вью.
     * При этом передаем в конфигуратор начальные данные, viewModelStore и lifecycleScope.
     * На основе этих данных будет создана вьюмодель виджета и проинициализировано начальное состояние.
     *
     * Если виджет находится в ресайклере то при первом байндинге будет создана вьюмодель и
     * проинициализировано состояние, а при повторном вью получит уже проинициализированную вьюмодель
     * на которую нужно будет переподписаться.
     *
     * При этом подписки, которые были у виджета отменяются, чтобы не получать данные от старых вьюмоделей
     * и создаются новые подписки чтоыб начать получать от новой вьюмодели.
     *
     * Если виджет не планируется использовать в ресайклере то хранить Job'ы и отменять их не обязательно.
     */
    fun bindData(initialData: ProductUi, viewModelStore: ViewModelStore, lifecycleScope: LifecycleCoroutineScope) {
        Logger.d("ProductWidget: bindData $initialData")
        ProductWidgetConfigurator().configure(this, viewModelStore, lifecycleScope, initialData)
        bindInternal()
    }
}