package ru.surfstudio.android.mvvmsandbox.network

import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList
import ru.surfstudio.android.mvvmsandbox.domain.Pagination
import ru.surfstudio.android.mvvmsandbox.interaction.catalog.data.PaginationObj

/**
 * Трансформирует коллекцию [Transformable] элементов
 */
fun <R, T : Transformable<R>> List<T>?.transformCollection(): List<R> {
    return this?.map { it.transform() } ?: emptyList()
}

/**
 * Трансформирует коллекцию в [AppDataList] с помощью [PaginationObj]
 * @return [AppDataList]
 */
fun <T> Collection<T>.toDataList(
    paginationObj: PaginationObj?
): DataList<T> {
    val paginationData = paginationObj?.transform() ?: Pagination()
    return DataList(
            this,
            paginationData.currentPage,
            paginationData.pageSize,
            paginationData.totalResults,
            paginationData.totalPages
    )
}
