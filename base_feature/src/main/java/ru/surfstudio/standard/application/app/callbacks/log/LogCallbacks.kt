package ru.surfstudio.standard.application.app.callbacks.log

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.surfstudio.standard.ui.activity.callbacks.DefaultActivityLifecycleCallbacks

/**
 * Колбеки для активити, логирующие стадии жизненного цикла при помощи [LogLifecycleObserver]
 */
class LifecycleLogActivityCallbacks(
        private val lifecycleLogObserver: LogLifecycleObserver,
        private val lifecycleLogFragmentCallbacks: LifecycleLogFragmentCallbacks
): DefaultActivityLifecycleCallbacks() {

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        if (p0 is AppCompatActivity) {
            p0.lifecycle.addObserver(lifecycleLogObserver)
            p0.supportFragmentManager.registerFragmentLifecycleCallbacks(lifecycleLogFragmentCallbacks, true)
        }
    }

    override fun onActivityDestroyed(p0: Activity) {
        if (p0 is AppCompatActivity) {
            p0.lifecycle.removeObserver(lifecycleLogObserver)
        }
    }
}

/**
 * Колбеки для фрагментов, логирующие стадии жизненного цикла при помощи [LogLifecycleObserver]
 */
class LifecycleLogFragmentCallbacks(
        private val lifecycleLogObserver: LogLifecycleObserver
) : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        f.lifecycle.addObserver(lifecycleLogObserver)
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        f.lifecycle.removeObserver(lifecycleLogObserver)
    }
}