package ru.surfstudio.android.mvvmsandbox.widget

import dagger.Module
import dagger.Provides

/**
 * Модуль, позволяющий провайдить аргументы типа [T]
 */
@Module
abstract class CustomArgsModule<T>(
        private val args: T
) {

    @Provides
    fun providesArgs(): T {
        return args
    }
}