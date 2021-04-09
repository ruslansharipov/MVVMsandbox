package ru.surfstudio.standard.ui.mvvm.view_model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * Интерфейс для удобного перевода Flow в фоновый поток
 */
interface FlowBuilderIo {

    /**
     * Переводит выполнение в фоновый поток
     */
    fun <T> Flow<T>.io(): Flow<T> {
        return this.flowOn(Dispatchers.IO)
    }
}