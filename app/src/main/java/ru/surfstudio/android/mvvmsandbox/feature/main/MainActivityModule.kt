package ru.surfstudio.android.mvvmsandbox.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.surfstudio.android.mvvmsandbox.feature.di.ViewModelKey
import ru.surfstudio.android.mvvmsandbox.view_model.DaggerViewModelFactory

@Module
class MainActivityModule {

    @Provides
    fun provideViewModel(viewModelStore: ViewModelStore, factory: DaggerViewModelFactory) : MainViewModel {
        return ViewModelProvider(viewModelStore, factory).get(MainViewModel::class.java)
    }

}

@Module
abstract class MainActivityViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindsMainViewModel(viewModel: MainViewModel): ViewModel
}
