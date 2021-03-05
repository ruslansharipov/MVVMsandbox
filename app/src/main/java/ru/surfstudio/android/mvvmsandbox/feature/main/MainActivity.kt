package ru.surfstudio.android.mvvmsandbox.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.surfstudio.android.mvvmsandbox.R
import ru.surfstudio.android.mvvmsandbox.activity.ActivityModule
import ru.surfstudio.android.mvvmsandbox.activity.DaggerActivityComponent
import ru.surfstudio.android.mvvmsandbox.app.App
import ru.surfstudio.android.navigation.provider.container.FragmentNavigationContainer
import javax.inject.Inject

class MainActivity : AppCompatActivity(), FragmentNavigationContainer {

    override val containerId: Int = R.id.main_fragment_container

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val activityComponent = DaggerActivityComponent.builder()
                .appComponent((application as App).appComponent)
                .activityModule(ActivityModule(viewModelStore))
                .build()

        DaggerMainComponent.builder()
                .activityComponent(activityComponent)
                .mainActivityModule(MainActivityModule())
                .build()
                .inject(this)
    }
}