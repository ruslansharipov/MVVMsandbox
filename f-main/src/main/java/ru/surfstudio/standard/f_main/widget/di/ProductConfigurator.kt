package ru.surfstudio.standard.f_main.widget.di

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.standard.f_main.widget.ProductViewModel
import ru.surfstudio.standard.f_main.widget.ProductViewModelImpl
import ru.surfstudio.standard.f_main.widget.ProductWidget
import ru.surfstudio.standard.f_main.widget.data.ProductUi
import ru.surfstudio.standard.ui.activity.di.ActivityComponent
import ru.surfstudio.standard.ui.configuration.InjectionTarget
import ru.surfstudio.standard.ui.configuration.configurator.WidgetConfigurator
import ru.surfstudio.standard.ui.configuration.component.ScreenComponent
import ru.surfstudio.standard.ui.mvvm.ProviderViewModelFactory
import ru.surfstudio.standard.ui.screen.di.CustomArgsModule
import ru.surfstudio.standard.ui.screen.di.ViewModelStoreModule
import ru.surfstudio.standard.ui.widget.di.LifecycleScopeModule
import javax.inject.Provider

/**
 * Пример конфигуратора для виджета.
 */
class ProductWidgetConfigurator : WidgetConfigurator<ProductUi>() {

    /**
     * Если виджет будет использовать какие-то биндинги/mvi то его компонент скорее всего будет
     * BindableScreenComponent, чтобы провайдить Any
     */
    @PerScreen
    @Component(
            dependencies = [ActivityComponent::class],
            modules = [
                ProductModule::class,
                ViewModelStoreModule::class,
                LifecycleScopeModule::class
            ]
    )
    internal interface ProductComponent : ScreenComponent<ProductWidget>

    /**
     * CustomArgsModule позволяет прокидывать начальные данные для инициализации состояния экрана,
     * но если нужно что-то более сложное всегда можно дописать непосредственно модуль виджета
     */
    @Module
    internal class ProductModule(product: ProductUi) : CustomArgsModule<ProductUi>(product) {

        @Provides
        fun provideViewModel(
                viewModelStore: ViewModelStore,
                provider: Provider<ProductViewModelImpl>,
                product: ProductUi
        ): ProductViewModel {
            return ViewModelProvider(viewModelStore, ProviderViewModelFactory(provider)).get(
                    product.id,
                    ProductViewModelImpl::class.java
            )
        }
    }

    override fun createWidgetComponent(
            activityComponent: ActivityComponent,
            viewModelStore: ViewModelStore,
            lifecycleScope: LifecycleCoroutineScope,
            args: ProductUi
    ): ScreenComponent<out InjectionTarget> {
        return DaggerProductWidgetConfigurator_ProductComponent.builder()
                .activityComponent(activityComponent)
                .viewModelStoreModule(ViewModelStoreModule(viewModelStore))
                .lifecycleScopeModule(LifecycleScopeModule(lifecycleScope))
                .productModule(ProductModule(args))
                .build()
    }
}