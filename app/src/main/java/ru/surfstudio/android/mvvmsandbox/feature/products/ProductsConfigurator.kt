package ru.surfstudio.android.mvvmsandbox.feature.products

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.mvvmsandbox.activity.di.ActivityComponent
import ru.surfstudio.android.mvvmsandbox.activity.di.ActivityModule
import ru.surfstudio.android.mvvmsandbox.activity.di.DaggerActivityComponent
import ru.surfstudio.android.mvvmsandbox.app.App
import ru.surfstudio.android.mvvmsandbox.configurator.Configurator
import ru.surfstudio.android.mvvmsandbox.configurator.ScreenComponent
import ru.surfstudio.android.mvvmsandbox.feature.di.CustomScreenModule
import ru.surfstudio.android.mvvmsandbox.feature.di.ScreenScope
import ru.surfstudio.android.mvvmsandbox.feature.di.ViewModelStoreModule
import ru.surfstudio.android.mvvmsandbox.view_model.ProviderViewModelFactory
import ru.surfstudio.android.mvvmsandbox.view_model.di.ViewModelFactoryModule
import javax.inject.Provider

class ProductsScreenConfigurator(private val target: ProductsFragmentView) : Configurator {

    @ScreenScope
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [
            ProductsModule::class,
            ViewModelFactoryModule::class,
            ViewModelStoreModule::class
        ]
    )
    internal interface ProductsComponent: ScreenComponent

    @Module
    internal class ProductsModule(route: ProductsRoute) :
        CustomScreenModule<ProductsRoute>(route) {

        @Provides
        fun provideViewModel(
            viewModelStore: ViewModelStore,
            provider: Provider<ProductsViewModel>,
            route: ProductsRoute
        ): IProductsViewModel {
            return ViewModelProvider(viewModelStore, ProviderViewModelFactory(provider)).get(
                route.getId(),
                ProductsViewModel::class.java
            )
        }
    }

    override fun createComponent(): ScreenComponent {
        val activity = target.activity
        val activityComponent = DaggerActivityComponent.builder()
            .appComponent((activity?.application as App).appComponent)
            .activityModule(ActivityModule())
            .build()

        return DaggerProductsScreenConfigurator_ProductsComponent.builder()
            .activityComponent(activityComponent)
            .viewModelStoreModule(ViewModelStoreModule(target.viewModelStore))
            .productsModule(ProductsModule(ProductsRoute()))
            .build()
    }
}