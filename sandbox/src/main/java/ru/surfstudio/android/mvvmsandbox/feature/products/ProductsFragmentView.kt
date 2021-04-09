package ru.surfstudio.android.mvvmsandbox.feature.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.surfstudio.android.easyadapter.pagination.EasyPaginationAdapter
import ru.surfstudio.android.mvvmsandbox.R
import ru.surfstudio.android.mvvmsandbox.configurator.HasConfigurator
import ru.surfstudio.android.mvvmsandbox.configurator.InjectionTarget
import ru.surfstudio.android.mvvmsandbox.pagination.PaginationFooterItemController
import javax.inject.Inject

class ProductsFragmentView : BottomSheetDialogFragment(), HasConfigurator, InjectionTarget {

    @Inject
    lateinit var viewModel: IProductsViewModel

    private lateinit var errorContainer: LinearLayout
    private lateinit var retryBtn: Button
    private lateinit var errorTv: TextView
    private lateinit var loadingPb: ProgressBar
    private lateinit var productsRv: RecyclerView

    private val easyAdapter = EasyPaginationAdapter(
        PaginationFooterItemController(R.layout.list_item_pagination_footer),
        { viewModel.loadMode() }
    )
    private lateinit var productController: ProductViewController

    override fun createConfigurator() = ProductsScreenConfigurator(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
        bindViews()
    }

    private fun initViews(view: View) {
        productController = ProductViewController(this.viewModelStore)

        loadingPb = view.findViewById(R.id.products_pb)
        errorTv = view.findViewById(R.id.products_error_tv)
        retryBtn = view.findViewById(R.id.products_retry_btn)
        errorContainer = view.findViewById(R.id.products_error_container)

        productsRv = view.findViewById<RecyclerView>(R.id.products_rv)
        productsRv.layoutManager = GridLayoutManager(requireContext(), 2)
        productsRv.adapter = easyAdapter

        retryBtn.setOnClickListener { viewModel.loadMode() }
    }

    private fun bindViews() {
        viewModel.products.observe(viewLifecycleOwner) { requestUi ->
            requestUi.data?.safeGet { dataList, paginationState ->
                easyAdapter.setData(dataList, productController, paginationState)
            }
            val hasData = requestUi.data?.hasData ?: false
            productsRv.isVisible = hasData
            loadingPb.isVisible = !hasData && requestUi.isLoading
            errorContainer.isVisible = !hasData && !requestUi.isLoading && requestUi.hasError

            errorTv.text = requestUi.error?.message
        }
        viewModel.toasts.observe(viewLifecycleOwner){ toast: String ->
            Toast.makeText(requireContext(), toast, Toast.LENGTH_SHORT).show()
        }
    }
}
