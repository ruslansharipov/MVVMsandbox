package ru.surfstudio.standard.i_initialization

import ru.surfstudio.android.app.migration.AppMigrationManager
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.standard.base.flow.CompletableFlow
import ru.surfstudio.standard.base.flow.completableFlow
import javax.inject.Inject

/**
 * Инициализирует приложение
 */
@PerApplication
class InitializeAppInteractor @Inject constructor(
        private val appMigrationManager: AppMigrationManager
) {

    /**
     * инициализирует приложение
     *
     * @return observable, который всегда завершается успешно
     */
    fun initialize(): CompletableFlow {
        return completableFlow {
            appMigrationManager.tryMigrateApp()
        }
    }
}