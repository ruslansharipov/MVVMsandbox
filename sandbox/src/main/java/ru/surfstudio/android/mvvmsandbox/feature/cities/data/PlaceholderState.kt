package ru.surfstudio.android.mvvmsandbox.feature.cities.data

import ru.surfstudio.android.core.mvp.binding.rx.request.data.Loading

sealed class PlaceholderState(override val isLoading: Boolean) : Loading {

    object None : PlaceholderState(isLoading = false)
    object MainLoading : PlaceholderState(isLoading = true)
    object Error : PlaceholderState(isLoading = false)
    object NoInternet : PlaceholderState(isLoading = false)

}
