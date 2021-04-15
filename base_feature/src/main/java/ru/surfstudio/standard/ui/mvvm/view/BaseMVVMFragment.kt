package ru.surfstudio.standard.ui.mvvm.view

import androidx.fragment.app.Fragment
import ru.surfstudio.standard.ui.configuration.HasFragmentConfigurator

/**
 * Базовый класс фрагмента, для использования с вью моделью
 */
abstract class BaseMVVMFragment: Fragment(), LifecycleMVVMView, HasFragmentConfigurator