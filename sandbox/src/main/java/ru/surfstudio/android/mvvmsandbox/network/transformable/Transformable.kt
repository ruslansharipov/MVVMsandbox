package ru.surfstudio.android.mvvmsandbox.network.transformable

import ru.surfstudio.android.mvvmsandbox.network.response.BaseResponse

interface Transformable<T> : BaseResponse {

    fun transform(): T
}