package ru.surfstudio.standard.f_main

import android.os.Bundle
import ru.surfstudio.standard.f_main.di.MainScreenConfigurator
import ru.surfstudio.standard.ui.configurator.Configurator
import ru.surfstudio.standard.ui.mvvm.view.BaseMVVMActivityView
import javax.inject.Inject

internal class MainActivityView: BaseMVVMActivityView() {

    @Inject
    lateinit var viewModel: MainViewModel

    override fun createConfigurator(): Configurator = MainScreenConfigurator(viewModelStore, intent)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}