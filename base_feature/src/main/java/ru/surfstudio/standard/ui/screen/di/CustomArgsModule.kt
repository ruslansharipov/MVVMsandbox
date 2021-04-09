package ru.surfstudio.standard.ui.screen.di

import dagger.Module
import dagger.Provides
import ru.surfstudio.android.dagger.scope.PerScreen

/**
 * Модуль, позволяющий провайдить аргументы типа [T]
 * Может понадобиться для передачи аргументов в виджеты, чтобы у них на старте уже были какие-то
 * начальные данные
 */
@Module
abstract class CustomArgsModule<T>(
        private val args: T
) {

    @Provides
    @PerScreen
    fun providesArgs(): T {
        return args
    }
}