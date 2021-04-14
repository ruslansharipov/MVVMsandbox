package ru.surfstudio.standard.application.app.callbacks.di

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import ru.surfstudio.standard.ui.activity.callbacks.DefaultActivityLifecycleCallbacks
import ru.surfstudio.standard.ui.configurator.Configurator
import ru.surfstudio.standard.ui.configurator.HasConfigurator
import ru.surfstudio.standard.ui.configurator.InjectionTarget

/**
 * Колбеки, предоставляющие зависимости активити
 *
 * Работают совместно с интерфейсами [HasConfigurator] и [InjectionTarget]
 * Если активити их реализует, то создается конфигуратор и вызывается метод [Configurator.configure]
 *
 * Также для всех FragmentActivity регистрируются [DiFragmentCallbacks] рекурсивно предоставляющие
 * зависимости фрагментам по аналогичному принципу
 */
class DiActivityCallbacks(
        private val diFragmentCallbacks: DiFragmentCallbacks
) : DefaultActivityLifecycleCallbacks() {

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        if (p0 is HasConfigurator && p0 is InjectionTarget) {
            p0.createConfigurator().configure(p0)
        }
        if (p0 is FragmentActivity) {
            p0.supportFragmentManager.registerFragmentLifecycleCallbacks(diFragmentCallbacks, true)
        }
    }
}

/**
 * Колбеки, предоставляющие зависимости фрагментам.
 */
class DiFragmentCallbacks : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        if (f is HasConfigurator && f is InjectionTarget) {
            f.createConfigurator().configure(f)
        }
    }
}