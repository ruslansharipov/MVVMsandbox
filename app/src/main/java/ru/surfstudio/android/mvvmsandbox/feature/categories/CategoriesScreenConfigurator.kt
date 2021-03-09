package ru.surfstudio.android.mvvmsandbox.feature.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.surfstudio.android.mvvmsandbox.activity.ActivityComponent
import ru.surfstudio.android.mvvmsandbox.activity.ActivityModule
import ru.surfstudio.android.mvvmsandbox.activity.DaggerActivityComponent
import ru.surfstudio.android.mvvmsandbox.app.App
import ru.surfstudio.android.mvvmsandbox.feature.di.CustomScreenModule
import ru.surfstudio.android.mvvmsandbox.feature.di.ScreenScope
import ru.surfstudio.android.mvvmsandbox.feature.di.ViewModelKey
import ru.surfstudio.android.mvvmsandbox.view_model.DaggerViewModelFactory
import ru.surfstudio.android.mvvmsandbox.view_model.di.ViewModelFactoryModule

internal class CategoriesScreenConfigurator {

    @ScreenScope
    @Component(
            dependencies = [ActivityComponent::class],
            modules = [
                CategoriesModule::class,
                ViewModelFactoryModule::class,
                CategoriesViewModelModule::class
            ]
    )
    internal interface CategoriesComponent {
        fun inject(view: CategoriesFragmentView)
    }

    @Module
    internal class CategoriesModule(route: CategoriesRoute) : CustomScreenModule<CategoriesRoute>(route) {

        @Provides
        fun provideViewModel(
                viewModelStore: ViewModelStore,
                factory: DaggerViewModelFactory,
                route: CategoriesRoute
        ): ICategoriesViewModel {
            return ViewModelProvider(viewModelStore, factory).get(route.getId(), CategoriesViewModel::class.java)
        }
    }

    @Module
    internal abstract class CategoriesViewModelModule {

        @Binds
        @IntoMap
        @ViewModelKey(CategoriesViewModel::class)
        abstract fun bindsFavoritesViewModel(viewModel: CategoriesViewModel): ViewModel
    }

    fun inject(fragment: CategoriesFragmentView) {
        val activity = fragment.activity
        val activityComponent = DaggerActivityComponent.builder()
                .appComponent((activity?.application as App).appComponent)
                .activityModule(ActivityModule(activity.viewModelStore))
                .build()

        DaggerCategoriesScreenConfigurator_CategoriesComponent.builder()
                .activityComponent(activityComponent)
                .categoriesModule(CategoriesModule(CategoriesRoute(fragment.requireArguments())))
                .build()
                .inject(fragment)
    }
}