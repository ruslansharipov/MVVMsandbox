package ru.surfstudio.android.mvvmsandbox.feature.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.surfstudio.android.mvvmsandbox.R
import javax.inject.Inject

class CategoriesFragmentView: Fragment() {

    @Inject
    lateinit var viewModel: ICategoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CategoriesScreenConfigurator().inject(this)
        Log.d("FavoritesFragmentView", viewModel.hashCode().toString())
    }
}