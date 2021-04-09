package ru.surfstudio.android.mvvmsandbox.app.di.callbacks

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import ru.surfstudio.android.mvvmsandbox.activity.callbacks.DefaultActivityLifecycleCallbacks
import ru.surfstudio.android.mvvmsandbox.configurator.HasConfigurator
import ru.surfstudio.android.mvvmsandbox.configurator.Injectable

/**
 * Колбеки, предоставляющие зависимости активити
 */
class DiActivityCallbacks : DefaultActivityLifecycleCallbacks() {

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        if (p0 is HasConfigurator && p0 is Injectable) {
            p0.createConfigurator().configure(p0)
        }
        if (p0 is FragmentActivity) {
            p0.supportFragmentManager.registerFragmentLifecycleCallbacks(DiFragmentCallbacks(), true)
        }
    }
}

/**
 * Колбеки, предоставляющие зависимости фрагментам.
 */
class DiFragmentCallbacks : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        if (f is HasConfigurator && f is Injectable) {
            f.createConfigurator().configure(f)
        }
    }
}