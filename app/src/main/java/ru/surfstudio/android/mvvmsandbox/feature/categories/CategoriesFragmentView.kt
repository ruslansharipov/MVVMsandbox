package ru.surfstudio.android.mvvmsandbox.feature.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.mvvmsandbox.R
import ru.surfstudio.android.mvvmsandbox.domain.Category
import ru.surfstudio.android.mvvmsandbox.network.Request
import javax.inject.Inject

class CategoriesFragmentView : Fragment() {

    @Inject
    lateinit var viewModel: ICategoriesViewModel

    private lateinit var placeholderTv: TextView
    private lateinit var categoriesRv: RecyclerView

    private val categoryAdapter = EasyAdapter()
    private val categoryController = CategoryController { category: Category ->
        viewModel.categoryClick(category)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CategoriesScreenConfigurator().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView(view)
        observeCategories()
    }

    private fun observeCategories() {
        viewModel.categories.observe(viewLifecycleOwner) { request ->
            when (request) {
                is Request.Error -> {
                    placeholderTv.text = "Ошибка: ${request.error.message}"
                }
                is Request.Loading -> {
                    placeholderTv.text = "Загрузка..."
                }
                is Request.Success -> {
                    categoryAdapter.setData(request.data, categoryController)
                }
            }
            placeholderTv.isVisible = request !is Request.Success
            categoriesRv.isVisible = request is Request.Success
        }
    }

    private fun initView(view: View) {
        placeholderTv = view.findViewById(R.id.categories_placeholder_tv)
        categoriesRv = view.findViewById(R.id.categories_rv)

        categoriesRv.run {
            layoutManager = LinearLayoutManager(context)
            adapter = categoryAdapter
        }
    }
}