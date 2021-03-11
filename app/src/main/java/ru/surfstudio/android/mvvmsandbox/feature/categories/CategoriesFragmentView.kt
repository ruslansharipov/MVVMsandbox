package ru.surfstudio.android.mvvmsandbox.feature.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.mvvmsandbox.R
import ru.surfstudio.android.mvvmsandbox.domain.Category
import javax.inject.Inject

class CategoriesFragmentView : Fragment() {

    @Inject
    lateinit var viewModel: ICategoriesViewModel

    private lateinit var errorTv: TextView
    private lateinit var categoriesRv: RecyclerView
    private lateinit var loadingPb: ProgressBar

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
            when {
                request.isError -> {
                    errorTv.text = "Ошибка: ${request.getError().message}"
                }
                request.isLoading -> {
                    // Ничего не делаем
                }
                request.isSuccess -> {
                    categoryAdapter.setData(request.getData(), categoryController)
                }
            }
            loadingPb.isVisible = request.isLoading
            errorTv.isVisible = request.isError
            categoriesRv.isVisible = request.isSuccess
        }
    }

    private fun initView(view: View) {
        errorTv = view.findViewById(R.id.categories_error_tv)
        categoriesRv = view.findViewById(R.id.categories_rv)
        loadingPb = view.findViewById(R.id.categories_pb)

        categoriesRv.run {
            layoutManager = LinearLayoutManager(context)
            adapter = categoryAdapter
        }
    }
}