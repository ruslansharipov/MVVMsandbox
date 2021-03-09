package ru.surfstudio.android.mvvmsandbox.feature.di

import dagger.Module
import dagger.Provides
import ru.surfstudio.android.navigation.route.Route

@Module
abstract class CustomScreenModule<out R : Route>(private val route: R) {

    @Provides
    @ScreenScope
    fun provideRoute(): R {
        return route
    }
}