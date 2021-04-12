package ru.surfstudio.standard.ui.mvvm.view_model

import androidx.lifecycle.ViewModel

/**
 * Базовый класс вьюмодели, поддерживающей перевод потока
 * выполнения в фон и выполнение команд навигации
 */
abstract class BaseViewModel: ViewModel(), FlowBuilderIo, NavigationViewModel