package ru.surfstudio.standard.f_main.container

import android.os.Bundle
import ru.surfstudio.android.navigation.provider.container.FragmentNavigationContainer
import ru.surfstudio.standard.f_main.R
import ru.surfstudio.standard.f_main.container.di.MainScreenConfigurator
import ru.surfstudio.standard.f_main.widget.ProductWidget
import ru.surfstudio.standard.f_main.widget.data.ProductUi
import ru.surfstudio.standard.ui.configurator.Configurator
import ru.surfstudio.standard.ui.mvvm.view.BaseMVVMActivityView
import javax.inject.Inject

internal class MainActivityView: BaseMVVMActivityView(), FragmentNavigationContainer {

    @Inject
    lateinit var viewModel: IMainViewModel

    override val containerId: Int = R.id.main_fragment_container

    override val name: String = "MainActivityView"

    override fun createConfigurator(): Configurator = MainScreenConfigurator(viewModelStore, intent)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val productWidget = findViewById<ProductWidget>(R.id.main_product_widget)
        productWidget.bindData(viewModelStore, ProductUi("1", 123))
    }
}