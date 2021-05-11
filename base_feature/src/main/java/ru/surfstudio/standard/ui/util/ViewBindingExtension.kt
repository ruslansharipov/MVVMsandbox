package ru.surfstudio.standard.ui.util

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ru.surfstudio.android.core.ui.view_binding.ViewBindingProperty
import ru.surfstudio.android.core.ui.view_binding.viewBinding

/**
 * Создание ViewBinding для фрагмента с использованием LayoutInflater.
 *
 * Таким образом можно использовать созданный ViewBinding в методе onCreateView
 * и возвращать из него [ViewBinding.root]
 */
inline fun <F : Fragment, T : ViewBinding> Fragment.viewBinding(
    crossinline vbFactory: (LayoutInflater) -> T
): ViewBindingProperty<F, T> {
    return viewBinding { fragment: F -> vbFactory(LayoutInflater.from(fragment.requireContext())) }
}