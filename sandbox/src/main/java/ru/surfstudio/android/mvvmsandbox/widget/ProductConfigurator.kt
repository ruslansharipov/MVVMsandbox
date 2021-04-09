package ru.surfstudio.android.mvvmsandbox.widget

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.mvvmsandbox.activity.di.ActivityComponent
import ru.surfstudio.android.mvvmsandbox.activity.di.ActivityModule
import ru.surfstudio.android.mvvmsandbox.activity.di.DaggerActivityComponent
import ru.surfstudio.android.mvvmsandbox.app.App
import ru.surfstudio.android.mvvmsandbox.domain.Product
import ru.surfstudio.android.mvvmsandbox.feature.di.ScreenScope
import ru.surfstudio.android.mvvmsandbox.feature.di.ViewModelStoreModule
import ru.surfstudio.android.mvvmsandbox.view_model.ProviderViewModelFactory
import ru.surfstudio.android.mvvmsandbox.view_model.di.ViewModelFactoryModule
import javax.inject.Provider

class ProductWidgetConfigurator {

    @ScreenScope
    @Component(
            dependencies = [ActivityComponent::class],
            modules = [
                ProductModule::class,
                ViewModelFactoryModule::class,
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