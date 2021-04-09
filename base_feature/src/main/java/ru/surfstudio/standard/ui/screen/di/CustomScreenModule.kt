package ru.surfstudio.standard.ui.screen.di

import dagger.Module
import dagger.Provides
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.navigation.route.Route

@Module
abstract class CustomScreenModule<out R : Route>(private val route: R) {

    @Provides
    @PerScreen
    fun provideRoute(): R {
        return route
    }
}
