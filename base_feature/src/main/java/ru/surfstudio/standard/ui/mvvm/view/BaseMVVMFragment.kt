package ru.surfstudio.standard.ui.mvvm.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import ru.surfstudio.standard.ui.configuration.HasFragmentConfigurator

/**
 * Базовый класс фрагмента, для использования с вью моделью
 */
abstract class BaseMVVMFragment : Fragment(), HasFragmentConfigurator, MVVMView {

    override val coroutineScope: CoroutineScope get() = lifecycleScope

    override fun onDestroyView() {
        super.onDestroyView()
        /** Отменяем подписки на стейт и команды при уничтожении вью */
        coroutineScope.coroutineContext.cancelChildren()
    }
}