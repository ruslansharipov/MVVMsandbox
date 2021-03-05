package ru.surfstudio.android.mvvmsandbox.activity

import androidx.lifecycle.ViewModelStore
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(
    private val viewModelStore: ViewModelStore
) {

    @Provides
    fun provideViewModelStore(): ViewModelStore {
        return viewModelStore
    }
}