package ru.surfstudio.standard.application.app.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.dagger.scope.PerApplication

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
}
