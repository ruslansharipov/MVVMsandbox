package ru.surfstudio.standard.f_main.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnAttach
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.*
import kotlinx.coroutines.Job
import ru.surfstudio.android.logger.Logger
import ru.surfstudio.standard.f_main.R
import ru.surfstudio.standard.f_main.widget.data.ProductUi
import ru.surfstudio.standard.ui.configuration.InjectionTarget
import ru.surfstudio.standard.ui.configuration.configurator.findActivity
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

    @Inject
    override lateinit var coroutineScope: LifecycleCoroutineScope

    @Inject
    lateinit var viewModel: ProductViewModel

    private var productJob: Job? = null
    private var toastsJob: Job? = null

    private val priceTv: TextView

    init {
        View.inflate(context, R.layout.widget_product_view, this)

        priceTv = findViewById(R.id.product_price_tv)
        priceTv.setOnClickListener { viewModel.onFavoriteClick() }

        doOnAttach {
            coroutineScope = try {
                findFragment<Fragment>().lifecycleScope
            } catch (e: Throwable) {
                (context.findActivity() as AppCompatActivity).lifecycleScope
            }
            checkAndBind()
        }
    }

    private fun checkAndBind() {
        if (::viewModel.isInitialized && ::coroutineScope.isInitialized) {
            bindInternal()
        }
    }

    private fun bindInternal() {
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
    fun bindData(initialData: ProductUi) {
        Logger.d("ProductWidget: bindData")
        ProductWidgetConfigurator().configure(this, initialData)
        checkAndBind()
    }
}