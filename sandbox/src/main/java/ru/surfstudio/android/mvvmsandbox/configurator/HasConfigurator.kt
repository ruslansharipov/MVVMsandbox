package ru.surfstudio.android.mvvmsandbox.configurator

/**
 * Интерфейс сущности у которой есть конфигуратор для получения зависимостей
 */
interface HasConfigurator {

    fun createConfigurator(): Configurator
}