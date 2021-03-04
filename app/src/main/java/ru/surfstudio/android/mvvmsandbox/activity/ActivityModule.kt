package ru.surfstudio.android.mvvmsandbox.activity

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import dagger.Module
import dagger.Provides
import ru.surfstudio.android.mvvmsandbox.view_model.DaggerViewModelFactory
import ru.surfstudio.android.mvvmsandbox.view_model.di.ViewModelFactoryModule

@Module
class ActivityModule(
    private val viewModelStore: ViewModelStore
) {

//    @Provides
//    fun provideViewModelStore(): ViewModelStore {
//        return viewModelStore
//    }

//    @Provides
//    fun provideViewModel(factory: DaggerViewModelFactory) : MainViewModel {
//        return ViewModelProvider(viewModelStore, factory).get(MainViewModel::class.java)
//    }

    @Provides
    fun providesFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory {
        return factory
    }

}