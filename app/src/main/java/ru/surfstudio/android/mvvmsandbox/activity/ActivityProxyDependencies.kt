package ru.surfstudio.android.mvvmsandbox.activity

import androidx.lifecycle.ViewModelStore
import ru.surfstudio.android.mvvmsandbox.view_model.DaggerViewModelFactory

interface ActivityProxyDependencies {

    fun providersFactory(): DaggerViewModelFactory
    fun viewModelStore(): ViewModelStore
}
