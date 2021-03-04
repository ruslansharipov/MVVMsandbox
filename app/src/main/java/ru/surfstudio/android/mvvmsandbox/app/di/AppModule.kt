package ru.surfstudio.android.mvvmsandbox.app.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.mvvmsandbox.view_model.di.ViewModelFactoryModule
import javax.inject.Singleton

@Module
class AppModule(
    private val app: Application
) {

    @Provides
    @Singleton
    internal fun provideContext(): Context = app

    @Provides
    @Singleton
    internal fun provideApp(): Application = app
}
