package ru.surfstudio.standard.f_main.bar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.surfstudio.android.navigation.provider.container.TabFragmentNavigationContainer
import ru.surfstudio.standard.f_main.R
import ru.surfstudio.standard.f_main.bar.di.MainBarScreenConfigurator
import ru.surfstudio.standard.ui.configuration.configurator.FragmentConfigurator
import ru.surfstudio.standard.ui.mvvm.view.BaseMVVMFragment

internal class MainBarFragmentView : BaseMVVMFragment(), TabFragmentNavigationContainer {

    override val name: String = "MainBarFragment"

    override val containerId: Int = R.id.main_bar_fragment_container

    override fun createConfigurator(): FragmentConfigurator = MainBarScreenConfigurator()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_bar, container, false)
    }
}