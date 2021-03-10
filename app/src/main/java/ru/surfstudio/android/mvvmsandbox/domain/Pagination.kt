package ru.surfstudio.android.mvvmsandbox.domain

/**
 * Описание пагинации
 */
data class Pagination(
    val totalResults: Int = 0,
    val totalPages: Int = 0,
    val pageSize: Int = 0,
    val currentPage: Int = 0
)
