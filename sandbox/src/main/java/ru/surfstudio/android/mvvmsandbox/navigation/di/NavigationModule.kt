package ru.surfstudio.android.mvvmsandbox.navigation.di

import android.content.Context
import androidx.core.content.ContextCompat
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import ru.surfstudio.android.navigation.observer.ScreenResultEmitter
import ru.surfstudio.android.navigation.observer.ScreenResultObserver
import ru.surfstudio.android.navigation.observer.bus.ScreenResultBus
import ru.surfstudio.android.navigation.observer.executor.AppCommandExecutorWithResult
import ru.surfstudio.android.navigation.observer.storage.ScreenResultStorage
import ru.surfstudio.android.navigation.observer.storage.file.FileScreenResultStorage
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
        screenResultEmitter: ScreenResultEmitter,
        activityNavigationProvider: ActivityNavigationProvider
    ): AppCommandExecutor {
        return AppCommandExecutorWithResult(screenResultEmitter, activityNavigationProvider)
    }

    @Provides
    @Singleton
    fun provideScreenResultStorage(context: Context): ScreenResultStorage {
        val filesDir = ContextCompat.getNoBackupFilesDir(context)!!.absolutePath
        return FileScreenResultStorage(filesDir)
    }

    @Provides
    @Singleton
    fun provideScreenResultBus(storage: ScreenResultStorage): ScreenResultBus {
        return ScreenResultBus(storage)
    }

    @Provides
    @Singleton
    fun provideScreenResultObserver(screenResultBus: ScreenResultBus): ScreenResultObserver {
        return screenResultBus
    }

    @Provides
    @Singleton
    fun provideScreenResultEmitter(screenResultBus: ScreenResultBus): ScreenResultEmitter {
        return screenResultBus
    }
}
