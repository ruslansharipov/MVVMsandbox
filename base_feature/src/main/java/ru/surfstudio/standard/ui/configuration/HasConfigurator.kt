package ru.surfstudio.standard.ui.configuration

import ru.surfstudio.standard.ui.configuration.configurator.ActivityConfigurator
import ru.surfstudio.standard.ui.configuration.configurator.FragmentConfigurator

/**
 * Интерфейс сущности у которой есть конфигуратор для получения зависимостей активити
 */
interface HasActivityConfigurator {
    fun createConfigurator() : ActivityConfigurator
}

/**
 * Интерфейс сущности у которой есть конфигуратор для получения зависимостей фрагмента
 */
interface HasFragmentConfigurator {
    fun createConfigurator() : FragmentConfigurator
}