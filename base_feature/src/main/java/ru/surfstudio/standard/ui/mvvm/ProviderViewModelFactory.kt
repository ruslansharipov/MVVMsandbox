package ru.surfstudio.standard.ui.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider

/**
 * Фабрика вьюмодели, работающая на основе [Provider], генерируемого даггером для вьюмодели, у
 * которой проставлена аннотация @Inject constructor.
 */
class ProviderViewModelFactory<T: ViewModel>(
    private val provider: Provider<T>
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return provider.get() as T
    }
}