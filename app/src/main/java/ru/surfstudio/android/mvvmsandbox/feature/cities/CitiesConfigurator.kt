package ru.surfstudio.android.mvvmsandbox.feature.cities

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

internal class CitiesScreenConfigurator {

    @ScreenScope
    @Component(
        dependencies = [ActivityComponent::class],
        modules = [
            CitiesModule::class,
            ViewModelFactoryModule::class,
            ViewModelStoreModule::class
        ]
    )
    internal interface CitiesComponent {
        fun inject(view: CitiesFragmentView)
    }

    @Module
    internal class CitiesModule(route: CitiesRoute) :
        CustomScreenModule<CitiesRoute>(route) {

        @Provides
        fun provideViewModel(
            viewModelStore: ViewModelStore,
            provider: Provider<CitiesViewModelImpl>,
            route: CitiesRoute
        ): CitiesViewModel {
            return ViewModelProvider(viewModelStore, ProviderViewModelFactory(provider)).get(
                route.getId(),
                CitiesViewModelImpl::class.java
            )
        }
    }

    fun inject(fragment: CitiesFragmentView) {
        val activity = fragment.activity
        val activityComponent = DaggerActivityComponent.builder()
            .appComponent((activity?.application as App).appComponent)
            .activityModule(ActivityModule())
            .build()

        DaggerCitiesScreenConfigurator_CitiesComponent.builder()
            .activityComponent(activityComponent)
            .viewModelStoreModule(ViewModelStoreModule(fragment.viewModelStore))
            .citiesModule(CitiesModule(CitiesRoute()))
            .build()
            .inject(fragment)
    }
}