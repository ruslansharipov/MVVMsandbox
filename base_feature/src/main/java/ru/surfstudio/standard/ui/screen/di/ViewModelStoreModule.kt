package ru.surfstudio.standard.ui.screen.di

import androidx.lifecycle.ViewModelStore
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.dagger.scope.PerScreen

/**
 * Модуль, который провайдит [ViewModelStore]
 */
@Module
class ViewModelStoreModule(
    private val viewModelStore: ViewModelStore
) {

    @Provides
    @PerScreen
    fun providesViewModelStore(): ViewModelStore {
        return viewModelStore
    }
}