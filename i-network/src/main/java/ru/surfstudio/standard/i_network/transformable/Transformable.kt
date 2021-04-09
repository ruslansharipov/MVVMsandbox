package ru.surfstudio.standard.i_network.transformable

import ru.surfstudio.standard.i_network.response.BaseResponse

interface Transformable<T> : BaseResponse {

    fun transform(): T
}