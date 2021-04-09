package ru.surfstudio.android.mvvmsandbox.navigation.view.model

import ru.surfstudio.android.navigation.command.NavigationCommand
import ru.surfstudio.android.navigation.executor.AppCommandExecutor

interface NavigationViewModel {

    val navCommandExecutor: AppCommandExecutor

    fun NavigationCommand.execute() {
        navCommandExecutor.execute(this)
    }

    fun List<NavigationCommand>.execute() {
        navCommandExecutor.execute(this)
    }
}