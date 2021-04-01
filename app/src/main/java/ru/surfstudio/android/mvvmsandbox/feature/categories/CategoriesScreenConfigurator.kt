package ru.surfstudio.android.mvvmsandbox.feature.categories

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.mvvmsandbox.activity.di.ActivityComponent
import ru.surfstudio.android.mvvmsandbox.activity.di.ActivityModule
import ru.surfstudio.android.mvvmsandbox.activity.di.DaggerActivityComponent
import ru.surfstudio.android.mvvmsandbox.app.App
import ru.surfstudio.android.mvvmsandbox.feature.di.CustomScreenModule
import ru.surfstudio.android.mvvmsandbox.feature.di.ScreenScope
import ru.surfstudio.android.mvvmsandbox.feature.di.ViewModelStoreModule
import ru.surfstudio.android.mvvmsandbox.view_model.ProviderViewModelFactory
import ru.surfstudio.android.mvvmsandbox.view_model.di.ViewModelFactoryModule
import javax.inject.Provider

internal class CategoriesScreenConfigurator {

    @ScreenScope
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [
            CategoriesModule::class,
            ViewModelFactoryModule::class,
            ViewModelStoreModule::class
        ]
    )
    internal interface CategoriesComponent {
        fun inject(view: CategoriesFragmentView)
    }

    @Module
    internal class CategoriesModule(route: CategoriesRoute) :
        CustomScreenModule<CategoriesRoute>(route) {

        @Provides
        fun provideViewModel(
            viewModelStore: ViewModelStore,
            provider: Provider<CategoriesViewModel>,
            route: CategoriesRoute
        ): ICategoriesViewModel {
            return ViewModelProvider(viewModelStore, ProviderViewModelFactory(provider)).get(
                route.getId(),
                CategoriesViewModel::class.java
            )
        }
    }

    fun inject(fragment: CategoriesFragmentView) {
        val activity = fragment.activity
        val activityComponent = DaggerActivityComponent.builder()
            .appComponent((activity?.application as App).appComponent)
            .activityModule(ActivityModule())
            .build()

        DaggerCategoriesScreenConfigurator_CategoriesComponent.builder()
            .activityComponent(activityComponent)
            .viewModelStoreModule(ViewModelStoreModule(fragment.viewModelStore))
            .categoriesModule(CategoriesModule(CategoriesRoute(fragment.requireArguments())))
            .build()
            .inject(fragment)
    }
}