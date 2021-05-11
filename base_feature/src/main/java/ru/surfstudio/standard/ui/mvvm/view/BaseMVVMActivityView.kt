package ru.surfstudio.standard.ui.mvvm.view

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import ru.surfstudio.android.template.base_feature.R
import ru.surfstudio.standard.ui.configuration.HasActivityConfigurator

/**
 * Базовый класс Активити, для использования совместно с вью моделью
 */
abstract class BaseMVVMActivityView : AppCompatActivity(), MVVMView, HasActivityConfigurator {

    override val coroutineScope: CoroutineScope get() = lifecycleScope

    /**
     * @return activity rootView
     */
    protected open fun getRootView(): View {
        return (findViewById<View>(R.id.content) as ViewGroup).getChildAt(0)
    }
}