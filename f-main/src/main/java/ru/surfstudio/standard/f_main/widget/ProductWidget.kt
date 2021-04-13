package ru.surfstudio.standard.f_main.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Job
import ru.surfstudio.android.logger.Logger
import ru.surfstudio.standard.f_main.R
import ru.surfstudio.standard.f_main.widget.data.ProductUi
import ru.surfstudio.standard.ui.configurator.InjectionTarget
import ru.surfstudio.standard.ui.configurator.findActivity
import ru.surfstudio.standard.ui.lifecycle.FlowObserver
import ru.surfstudio.standard.f_main.widget.di.ProductWidgetConfigurator
import javax.inject.Inject

/**
 * Пример виджета с вьюмоделью
 *
 *
 */
class ProductWidget @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), FlowObserver, InjectionTarget {

    override lateinit var coroutineScope: LifecycleCoroutineScope

    private var productJob: Job?= null
    private var toastsJob: Job?= null

    @Inject
    lateinit var viewModel: ProductViewModel

    private val priceTv: TextView

    init {
        View.inflate(context, R.layout.widget_product_view, this)

        priceTv = findViewById(R.id.product_price_tv)
        priceTv.setOnClickListener { viewModel.onFavoriteClick() }
    }

    /**
     * Подписка на вьюмодель осуществляется в методе onAttachedToWindow, после инициализации
     * coroutineScope виджета.
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Logger.d("ProductWidget: onAttachedToWindow")
        coroutineScope = (context.findActivity() as LifecycleOwner).lifecycle.coroutineScope

        productJob?.cancel()
        productJob = viewModel.productState.bindTo { product: ProductUi ->
            priceTv.text = product.price.toString()
        }
        toastsJob?.cancel()
        toastsJob = viewModel.toasts.bindTo { toast ->
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * При привязке данных инициализируем конфигуратор и конфигурируем вью.
     * При этом передаем в конфигуратор начальные данные.
     * На основе этих данных будет создана вьюмодель экрана и проинициализировано начальное состояние.
     *
     * Если виджет находится в ресайклере то при первом байндинге будет создана вьюмодель и
     * проинициализировано состояние, а при повторном вью получит уже проинициализированную вьюмодель
     * на которую нужно будет переподписаться.
     */
    fun bindData(viewModelStore: ViewModelStore, initialData: ProductUi) {
        Logger.d("ProductWidget: bindData")
        ProductWidgetConfigurator(viewModelStore, initialData).configure(this)
    }
}