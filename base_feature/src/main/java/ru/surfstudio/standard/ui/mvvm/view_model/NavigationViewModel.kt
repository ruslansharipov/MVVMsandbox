package ru.surfstudio.standard.ui.mvvm.view_model

import ru.surfstudio.android.navigation.command.NavigationCommand
import ru.surfstudio.android.navigation.executor.AppCommandExecutor

/**
 * Интерфейс вьюмодели, поддерживающей выполнение команд навигации
 */
interface NavigationViewModel {

    val navCommandExecutor: AppCommandExecutor

    /**
     * Выполнение одной команды навигации
     */
    fun NavigationCommand.execute() {
        navCommandExecutor.execute(this)
    }

    /**
     * Выполнение нескольких команд навигации
     */
    fun List<NavigationCommand>.execute() {
        navCommandExecutor.execute(this)
    }
}