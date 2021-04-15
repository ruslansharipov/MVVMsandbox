package ru.surfstudio.standard.ui.widget.di

import androidx.lifecycle.LifecycleCoroutineScope
import dagger.Module
import dagger.Provides

@Module
class LifecycleScopeModule(
        private val scope: LifecycleCoroutineScope
) {

    @Provides
    fun providesLifecycleScope() : LifecycleCoroutineScope = scope
}