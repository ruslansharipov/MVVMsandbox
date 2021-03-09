package ru.surfstudio.android.mvvmsandbox.feature.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.surfstudio.android.mvvmsandbox.R
import javax.inject.Inject

class FavoritesFragmentView: Fragment() {

    @Inject
    lateinit var viewModel: IFavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FavoritesScreenConfigurator().inject(this)
        Log.d("FavoritesFragmentView", viewModel.hashCode().toString())
    }
}