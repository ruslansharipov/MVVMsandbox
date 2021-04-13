package ru.surfstudio.standard.f_splash.di

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.standard.f_splash.ISplashViewModel
import ru.surfstudio.standard.f_splash.SplashActivityView
import ru.surfstudio.standard.f_splash.SplashRoute
import ru.surfstudio.standard.f_splash.SplashViewModel
import ru.surfstudio.standard.ui.activity.di.ActivityComponent
import ru.surfstudio.standard.ui.configurator.InjectionTarget
import ru.surfstudio.standard.ui.configurator.Configurator
import ru.surfstudio.standard.ui.configurator.component.BindableScreenComponent
import ru.surfstudio.standard.ui.configurator.component.ScreenComponent
import ru.surfstudio.standard.ui.mvvm.ProviderViewModelFactory
import ru.surfstudio.standard.ui.screen.di.CustomScreenModule
import ru.surfstudio.standard.ui.screen.di.ViewModelStoreModule
import javax.inject.Provider

internal class SplashScreenConfigurator(
        private val viewModelStore: ViewModelStore,
        private val intent: Intent
): Configurator {

    @PerScreen
    @Component(
            dependencies = [ActivityComponent::class],
            modules = [
                SplashModule::class,
                ViewModelStoreModule::class
            ]
    )
    internal interface SplashComponent: BindableScreenComponent<SplashActivityView>

    @Module
    internal class SplashModule(route: SplashRoute) : CustomScreenModule<SplashRoute>(route) {

        @Provides
        fun provideViewModel(
                viewModelStore: ViewModelStore,
                provider: Provider<SplashViewModel>,
                route: SplashRoute
        ): ISplashViewModel {
            return ViewModelProvider(viewModelStore, ProviderViewModelFactory(provider)).get(
                    route.getId(),
                    SplashViewModel::class.java
            )
        }

        @Provides
        fun ensureViewModelIsCreated(viewModel: ISplashViewModel) : Any {
            return viewModel
        }
    }

    override fun createComponent(activityComponent: ActivityComponent): ScreenComponent<out InjectionTarget> {
        return DaggerSplashScreenConfigurator_SplashComponent.builder()
                .activityComponent(activityComponent)
                .viewModelStoreModule(ViewModelStoreModule(viewModelStore))
                .splashModule(SplashModule(SplashRoute(intent)))
                .build()
    }
}