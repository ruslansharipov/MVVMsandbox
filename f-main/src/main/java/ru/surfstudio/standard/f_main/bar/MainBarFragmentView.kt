package ru.surfstudio.standard.f_main.bar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.navigation.provider.container.TabFragmentNavigationContainer
import ru.surfstudio.standard.f_main.R
import ru.surfstudio.standard.f_main.bar.di.MainBarScreenConfigurator
import ru.surfstudio.standard.f_main.widget.ProductViewController
import ru.surfstudio.standard.f_main.widget.data.ProductUi
import ru.surfstudio.standard.ui.configuration.configurator.FragmentConfigurator
import ru.surfstudio.standard.ui.mvvm.view.BaseMVVMFragment

internal class MainBarFragmentView : BaseMVVMFragment(), TabFragmentNavigationContainer {

    private val easyAdapter = EasyAdapter()
    private lateinit var widgetController: ProductViewController

    override val name: String = "MainBarFragment"

    override val containerId: Int = R.id.main_bar_fragment_container

    override fun createConfigurator(): FragmentConfigurator = MainBarScreenConfigurator()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_bar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        widgetController = ProductViewController(viewModelStore, lifecycleScope)

        val recyclerView = view.findViewById<RecyclerView>(R.id.main_bar_recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = easyAdapter

        val products = List(100){ index: Int ->
            ProductUi(id = index.toString(), price = index)
        }
        easyAdapter.setData(products, widgetController)
    }
}