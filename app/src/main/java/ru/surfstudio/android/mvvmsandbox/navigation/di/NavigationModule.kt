package ru.surfstudio.android.mvvmsandbox.navigation.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import ru.surfstudio.android.navigation.provider.ActivityNavigationProvider
import ru.surfstudio.android.navigation.provider.callbacks.ActivityNavigationProviderCallbacks
import javax.inject.Singleton

@Module
class NavigationModule {

    @Provides
    @Singleton
    fun provideActivityNavigationProviderCallbacks(context: Context): ActivityNavigationProviderCallbacks {
        return ActivityNavigationProviderCallbacks()
    }

    @Provides
    @Singleton
    fun provideActivityNavigationProvider(
        activityNavigationProviderCallbacks: ActivityNavigationProviderCallbacks
    ): ActivityNavigationProvider {
        return activityNavigationProviderCallbacks
    }

    @Provides
    @Singleton
    fun provideAppCommandExecutor(
        activityNavigationProvider: ActivityNavigationProvider
    ): AppCommandExecutor {
        return AppCommandExecutor(activityNavigationProvider)
    }
}
