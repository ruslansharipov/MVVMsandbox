package ru.surfstudio.android.mvvmsandbox.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.surfstudio.android.mvvmsandbox.R
import ru.surfstudio.android.mvvmsandbox.configurator.HasConfigurator
import ru.surfstudio.android.mvvmsandbox.configurator.InjectionTarget
import ru.surfstudio.android.navigation.provider.container.FragmentNavigationContainer
import javax.inject.Inject

class MainActivity : AppCompatActivity(), FragmentNavigationContainer, HasConfigurator, InjectionTarget {

    override val containerId: Int = R.id.main_fragment_container

    @Inject
    lateinit var viewModel: IMainViewModel

    override fun createConfigurator() = MainScreenConfigurator(application, viewModelStore, intent)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("MainActivity", viewModel.toString())
    }

    override fun onBackPressed() {
        viewModel.onBackPress()
    }
}