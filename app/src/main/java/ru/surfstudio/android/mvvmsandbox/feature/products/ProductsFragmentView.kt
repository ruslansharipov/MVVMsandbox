package ru.surfstudio.android.mvvmsandbox.feature.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.surfstudio.android.easyadapter.pagination.EasyPaginationAdapter
import ru.surfstudio.android.mvvmsandbox.R
import ru.surfstudio.android.mvvmsandbox.pagination.PaginationFooterItemController
import javax.inject.Inject

class ProductsFragmentView : Fragment() {

    @Inject
    lateinit var viewModel: IProductsViewModel

    private val easyAdapter =
        EasyPaginationAdapter(PaginationFooterItemController(R.layout.list_item_pagination_footer)) {
            viewModel.loadMode()
        }
    private val productController = ProductViewController(
        onProductClick = { }, // TODO
        onFavoriteClick = { viewModel.onFavoriteClick(it) }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ProductsScreenConfigurator().inject(this)
        initViews(view)
        bindViews()
    }

    private fun initViews(view: View) {
        val productsRv = view.findViewById<RecyclerView>(R.id.products_rv)
        productsRv.layoutManager = GridLayoutManager(requireContext(), 2)
        productsRv.adapter = easyAdapter
    }

    private fun bindViews() {
        viewModel.products.observe(viewLifecycleOwner) { paginationBundle ->
            paginationBundle.data?.safeGet { dataList, paginationState ->
                easyAdapter.setData(dataList, productController, paginationState)
            }
        }
    }
}
