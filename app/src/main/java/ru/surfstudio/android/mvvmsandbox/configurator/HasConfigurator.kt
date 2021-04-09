package ru.surfstudio.android.mvvmsandbox.configurator

interface HasConfigurator {

    fun createConfigurator(): Configurator
}

interface Configurator {

    fun createComponent(): ScreenComponent

    fun configure(target: Injectable) {
        createComponent().inject(target)
    }
}

interface Injectable

interface ScreenComponent {
    fun inject(target: Injectable)
}