package ru.surfstudio.android.mvvmsandbox.pagination

import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList
import ru.surfstudio.android.easyadapter.pagination.PaginationState

/**
 * Пагинируемый список для отображения на UI.
 *
 * @param list список
 * @param state состояние пагинации
 */
data class PaginationBundle<T>(
    val list: DataList<T>? = null,
    val state: PaginationState? = null
) {

    val hasData = !list.isNullOrEmpty()

    /**
     * Получение данных и paginationState в том случае, когда они оба не null
     *
     * @param onDataReadyCallback коллбек, в котором будут содержаться безопсные данные
     */
    fun safeGet(onDataReadyCallback: (DataList<T>, PaginationState) -> Unit) {
        list ?: return
        state ?: return
        onDataReadyCallback(list, state)
    }
}
