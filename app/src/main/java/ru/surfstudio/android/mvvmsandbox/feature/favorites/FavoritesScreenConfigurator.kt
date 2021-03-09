package ru.surfstudio.android.mvvmsandbox.feature.favorites

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

internal class FavoritesScreenConfigurator {

    @ScreenScope
    @Component(
            dependencies = [ActivityComponent::class],
            modules = [
                FavoritesActivityModule::class,
                ViewModelFactoryModule::class,
                FavoritesActivityViewModelModule::class
            ]
    )
    internal interface FavoritesComponent {
        fun inject(view: FavoritesFragmentView)
    }

    @Module
    internal class FavoritesActivityModule(route: FavoritesRoute) : CustomScreenModule<FavoritesRoute>(route) {

        @Provides
        fun provideViewModel(
                viewModelStore: ViewModelStore,
                factory: DaggerViewModelFactory,
                route: FavoritesRoute
        ): IFavoritesViewModel {
            return ViewModelProvider(viewModelStore, factory).get(route.getId(), FavoritesViewModel::class.java)
        }
    }

    @Module
    internal abstract class FavoritesActivityViewModelModule {

        @Binds
        @IntoMap
        @ViewModelKey(FavoritesViewModel::class)
        abstract fun bindsFavoritesViewModel(viewModel: FavoritesViewModel): ViewModel
    }

    fun inject(fragment: FavoritesFragmentView) {
        val activity = fragment.activity
        val activityComponent = DaggerActivityComponent.builder()
                .appComponent((activity?.application as App).appComponent)
                .activityModule(ActivityModule(activity.viewModelStore))
                .build()

        DaggerFavoritesScreenConfigurator_FavoritesComponent.builder()
                .activityComponent(activityComponent)
                .favoritesActivityModule(FavoritesActivityModule(FavoritesRoute(fragment.requireArguments())))
                .build()
                .inject(fragment)
    }
}