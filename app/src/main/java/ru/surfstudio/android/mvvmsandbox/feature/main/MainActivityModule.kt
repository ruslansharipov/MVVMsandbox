package ru.surfstudio.android.mvvmsandbox.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.surfstudio.android.mvvmsandbox.feature.di.ViewModelKey

@Module
class MainActivityModule(
    private val viewModelStore: ViewModelStore
) {

//    @Provides
//    fun provideViewModelStore(): ViewModelStore {
//        return viewModelStore
//    }

//    @Provides
//    fun provideViewModel(factory: ViewModelProvider.Factory) : MainViewModel {
//        return ViewModelProvider(viewModelStore, factory).get(MainViewModel::class.java)
//    }

}

@Module
abstract class MainActivityViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindsMainViewModel(viewModel: MainViewModel): ViewModel
}
