package ru.surfstudio.android.mvvmsandbox.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider

class ProviderViewModelFactory<T: ViewModel>(
    private val provider: Provider<T>
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return provider.get() as T
    }
}