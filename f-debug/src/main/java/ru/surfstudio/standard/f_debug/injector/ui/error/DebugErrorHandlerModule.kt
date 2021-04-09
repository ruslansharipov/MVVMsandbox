package ru.surfstudio.standard.f_debug.injector.ui.error

import dagger.Module
import dagger.Provides
import ru.surfstudio.android.core.mvp.error.ErrorHandler
import ru.surfstudio.android.dagger.scope.PerScreen

@Module
class DebugErrorHandlerModule {

    @Provides
    @PerScreen
    fun provideNetworkErrorHandler(standardErrorHandler: DebugStandardErrorHandler): ErrorHandler {
        return standardErrorHandler
    }
}