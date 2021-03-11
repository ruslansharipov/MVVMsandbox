package ru.surfstudio.android.mvvmsandbox.util

import ru.surfstudio.android.core.mvi.ui.mapper.RequestDataMapper
import ru.surfstudio.android.core.mvi.ui.mapper.RequestLoadingMapper
import ru.surfstudio.android.core.mvp.binding.rx.request.data.SimpleLoading
import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList
import ru.surfstudio.android.easyadapter.pagination.PaginationState
import ru.surfstudio.android.mvvmsandbox.pagination.PaginationBundle

/**
 * Singleton-фабрика мапперов запросов.
 *
 * Используется для хранения переиспользуемых мапперов внутри проекта.
 * */
object RequestMappers {

    /**
     * Мапперы данных запроса.
     * */
    val data = DataMappers

    /**
     * Мапперы состояния загрузки запроса.
     * */
    val loading = LoadingMappers

    /**
     * Мапперы данных запроса.
     * */
    object DataMappers {

        /**
         * ## Маппер данных стандартного запроса.
         *
         * Следует использовать этот маппер в тех случаях, когда нам необходимо:
         *
         * * При первом запросе получить данные и сохранить их в стейт;
         * * При последующих запросах, если они удачные - обновить данные в стейте.
         *
         * @return только что полученные данные из запроса, либо данные из стейта.
         * */
        fun <T> default(): RequestDataMapper<T, T, T> =
            { request, data -> request.getDataOrNull() ?: data }

        /**
         * ## Маппер данных пагинируемого запроса.
         *
         * Следует использовать этот маппер в тех случаях, когда нам необходимо:
         *
         * * При первом запросе получить пагинируемые данные и
         * сохранить их в стейт с преобразованием в `PaginationBundle`;
         * * При последующих запросах, если они удачные - дополнить/обновить данные в стейте;
         *
         * @return только что полученные данные из запроса (опционально смерженные с данными из стейта),
         *         либо данные из стейта.
         * */
        fun <T> pagination(): RequestDataMapper<DataList<T>, PaginationBundle<T>, PaginationBundle<T>> =
            { request, paginationBundle ->
                val currentPageData = paginationBundle?.list
                val nextPageData = request.getDataOrNull()

                // Если загружена предыдущая страница, то не мержим ее, а берем как текущую.
                val isNextPageData = nextPageData?.nextPage ?: 0 > currentPageData?.nextPage ?: 0
                val isNotEmptyCurrentData = currentPageData != null
                val isNotEmptyNewPageData = nextPageData != null

                val canMergePageData = isNextPageData && isNotEmptyNewPageData && isNotEmptyCurrentData

                val mappedPageData = when {
                    canMergePageData -> currentPageData?.merge(nextPageData) as DataList
                    isNotEmptyNewPageData -> nextPageData
                    else -> currentPageData
                }

                val hasMorePageData = mappedPageData?.canGetMore() ?: false
                val mappedPaginationState = when {
                    request.isSuccess && hasMorePageData -> PaginationState.READY
                    request.isSuccess && !hasMorePageData -> PaginationState.COMPLETE
                    request.isError && !hasMorePageData -> PaginationState.COMPLETE
                    request.isError -> PaginationState.ERROR
                    else -> null
                }
                PaginationBundle(mappedPageData, mappedPaginationState)
            }
    }

    /**
     * Мапперы состояния загрузки запроса.
     * */
    object LoadingMappers {

        fun <T1, T2> simple(): RequestLoadingMapper<T1, T2> =
            { request, _ -> SimpleLoading(request.isLoading) }
    }
}
