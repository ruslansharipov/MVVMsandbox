package ru.surfstudio.android.mvvmsandbox.widget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Job
import ru.surfstudio.android.imageloader.ImageLoader
import ru.surfstudio.android.mvvmsandbox.R
import ru.surfstudio.android.mvvmsandbox.domain.Product
import ru.surfstudio.android.mvvmsandbox.lifecycle.FlowObserver
import javax.inject.Inject

class ProductWidget @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), FlowObserver {

    override lateinit var coroutineScope: LifecycleCoroutineScope
    
    private var productJob: Job ?= null
    private var toastsJob: Job ?= null

    @Inject
    lateinit var viewModel: ProductViewModel

    private val photoIv: ImageView
    private val addToFavoriteIv: ImageView
    private val nameTv: TextView
    private val priceTv: TextView
    private val shortDescriptionTv: TextView

    init {
        View.inflate(context, R.layout.widget_product_view, this)

        photoIv = findViewById(R.id.product_photo_iv)
        addToFavoriteIv = findViewById(R.id.product_add_favorite_iv)
        nameTv = findViewById(R.id.product_name_tv)
        priceTv = findViewById(R.id.product_price_tv)
        shortDescriptionTv = findViewById(R.id.product_short_description_tv)

        addToFavoriteIv.setOnClickListener { viewModel.onFavoriteClick() }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        coroutineScope = (getActivity() as LifecycleOwner).lifecycle.coroutineScope

        productJob?.cancel()
        productJob = viewModel.productState.bindTo { product: Product ->
            ImageLoader.with(context)
                    .error(drawableResId = R.drawable.ic_product_place_holder)
                    .preview(drawableResId = R.drawable.ic_product_place_holder)
                    .url(product.image)
                    .into(photoIv)

            addToFavoriteIv.isSelected = product.addedToWishlist
            nameTv.text = product.name
            shortDescriptionTv.text = product.descriptionShort
            priceTv.text = product.price.value.toString()
        }
        toastsJob?.cancel()
        toastsJob = viewModel.toasts.bindTo { toast ->
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
        }
    }

    fun bindData(viewModelStore: ViewModelStore, initialData: Product) {
        ProductWidgetConfigurator().inject(viewModelStore, initialData, this)
    }
}

fun View.getActivity(): Activity? {
    return when (val context = context) {
        is Activity -> context
        is ContextThemeWrapper -> context.baseContext as Activity?
        else -> null
    }
}
