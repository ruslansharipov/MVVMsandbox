package ru.surfstudio.standard.ui.mvvm.view

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import ru.surfstudio.android.template.base_feature.R

/**
 * Базовый класс Активити, для использования совместно с вью моделью
 */
abstract class BaseMVVMActivityView: AppCompatActivity(), MVVMView {

    /**
     * @return activity rootView
     */
    protected open fun getRootView(): View {
        return (findViewById<View>(R.id.content) as ViewGroup).getChildAt(0)
    }
}