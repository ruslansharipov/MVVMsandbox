package ru.surfstudio.android.mvvmsandbox.feature.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.surfstudio.android.mvvmsandbox.R
import javax.inject.Inject

class ProductsFragmentView: Fragment() {

    @Inject
    lateinit var viewModel: IProductsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ProductsScreenConfigurator().inject(this)
    }
}
