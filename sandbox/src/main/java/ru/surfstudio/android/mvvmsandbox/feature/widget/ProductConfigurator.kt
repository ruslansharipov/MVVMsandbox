package ru.surfstudio.android.mvvmsandbox.widget

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.mvvmsandbox.activity.di.DaggerActivityComponent
import ru.surfstudio.android.mvvmsandbox.domain.Product
import ru.surfstudio.standard.application.app.App
import ru.surfstudio.standard.ui.activity.di.ActivityComponent
import ru.surfstudio.standard.ui.activity.di.ActivityModule
import ru.surfstudio.standard.ui.mvvm.ProviderViewModelFactory
import ru.surfstudio.standard.ui.screen.di.CustomArgsModule
import ru.surfstudio.standard.ui.screen.di.ViewModelStoreModule
import ru.surfstudio.standard.ui.widget.ProductViewModel
import ru.surfstudio.standard.ui.widget.ProductViewModelImpl
import javax.inject.Provider

class ProductWidgetConfigurator {

    @PerScreen
    @Component(
            dependencies = [ActivityComponent::class],
            modules = [
                ProductModule::class,
                ViewModelStoreModule::class
            ]
    )
    internal interface ProductComponent {
        fun inject(view: ProductWidget)
    }

    @Module
    internal class ProductModule(product: Product): CustomArgsModule<Product>(product) {

        @Provides
        fun provideViewModel(
            viewModelStore: ViewModelStore,
            provider: Provider<ProductViewModelImpl>,
            product: Product
        ): ProductViewModel {
            return ViewModelProvider(viewModelStore, ProviderViewModelFactory(provider)).get(
                    product.code,
                    ProductViewModelImpl::class.java
            )
        }
    }

    fun inject(viewModelStore: ViewModelStore, product: Product, view: ProductWidget) {
        val activityComponent = DaggerActivityComponent.builder()
                .appComponent((view.context.applicationContext as App).appComponent)
                .activityModule(ActivityModule())
                .build()

        DaggerProductWidgetConfigurator_ProductComponent.builder()
                .activityComponent(activityComponent)
                .viewModelStoreModule(ViewModelStoreModule(viewModelStore))
                .productModule(ProductModule(product))
                .build()
                .inject(view)
    }
}