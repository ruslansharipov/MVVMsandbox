package ru.surfstudio.android.mvvmsandbox.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import ru.surfstudio.android.mvvmsandbox.R
import ru.surfstudio.android.mvvmsandbox.activity.DaggerActivityComponent
import ru.surfstudio.android.mvvmsandbox.app.App
import ru.surfstudio.android.mvvmsandbox.view_model.DaggerViewModelFactory
import ru.surfstudio.android.navigation.provider.container.FragmentNavigationContainer
import javax.inject.Inject

class MainActivity : AppCompatActivity(), FragmentNavigationContainer {

    override val containerId: Int = R.id.main_fragment_container
//
//    @Inject
//    lateinit var viewModelFactory: ViewModelProvider.Factory
//
//    private val viewModel: MainViewModel by viewModels { viewModelFactory }

//    @Inject
//    lateinit var viewModel: MainViewModel

//    private val vm: MainViewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val activityComponent = DaggerActivityComponent.builder()
            .appComponent((application as App).appComponent)
            .build()

        DaggerMainComponent.builder()
            .activityComponent(activityComponent)
            .mainActivityModule(MainActivityModule(viewModelStore))
            .build()
            .inject(this)
//        Log.d("MainActivity", "viewModel: $viewModel")
    }
}