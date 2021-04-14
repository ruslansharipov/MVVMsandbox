package ru.surfstudio.standard.application.app.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.connection.ConnectionProvider
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.standard.application.app.callbacks.di.DiActivityCallbacks
import ru.surfstudio.standard.application.app.callbacks.di.DiFragmentCallbacks
import ru.surfstudio.standard.application.app.callbacks.log.LifecycleLogActivityCallbacks
import ru.surfstudio.standard.application.app.callbacks.log.LifecycleLogFragmentCallbacks
import ru.surfstudio.standard.application.app.callbacks.log.LogLifecycleObserver
import ru.surfstudio.standard.i_network.network.interactor.ConnectionChecker
import ru.surfstudio.standard.i_network.network.interactor.ConnectionCheckerProvider

@Module
class AppModule(
        private val app: Application
) {

    @Provides
    @PerApplication
    internal fun provideContext(): Context = app

    @Provides
    @PerApplication
    internal fun provideApp(): Application = app

    @Provides
    @PerApplication
    internal fun provideConnectionProvider(context: Context): ConnectionProvider {
        return ConnectionProvider(context)
    }

    @Provides
    @PerApplication
    internal fun providesConnectionChecker(provider: ConnectionProvider): ConnectionChecker {
        return ConnectionCheckerProvider(provider)
    }

    @Provides
    @PerApplication
    internal fun providesDiFragmentCallbacks(): DiFragmentCallbacks {
        return DiFragmentCallbacks()
    }

    @Provides
    @PerApplication
    internal fun providesDiActivityCallbacks(diFragmentCallbacks: DiFragmentCallbacks): DiActivityCallbacks {
        return DiActivityCallbacks(diFragmentCallbacks)
    }

    @Provides
    @PerApplication
    internal fun providesLogLifecycleObserver(): LogLifecycleObserver {
        return LogLifecycleObserver()
    }

    @Provides
    @PerApplication
    internal fun providesLogFragmentCallbacks(logLifecycleObserver: LogLifecycleObserver): LifecycleLogFragmentCallbacks {
        return LifecycleLogFragmentCallbacks(logLifecycleObserver)
    }

    @Provides
    @PerApplication
    internal fun providesLogActivityCallbacks(
            logLifecycleObserver: LogLifecycleObserver,
            logFragmentCallbacks: LifecycleLogFragmentCallbacks
    ): LifecycleLogActivityCallbacks {
        return LifecycleLogActivityCallbacks(logLifecycleObserver, logFragmentCallbacks)
    }
}
