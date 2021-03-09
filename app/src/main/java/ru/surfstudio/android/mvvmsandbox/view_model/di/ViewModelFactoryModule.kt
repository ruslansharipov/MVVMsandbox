package ru.surfstudio.android.mvvmsandbox.view_model.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import ru.surfstudio.android.mvvmsandbox.feature.main.MainActivityViewModelModule
import ru.surfstudio.android.mvvmsandbox.view_model.DaggerViewModelFactory

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}