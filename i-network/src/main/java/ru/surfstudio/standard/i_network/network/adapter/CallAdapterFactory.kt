package ru.surfstudio.standard.i_network.network.adapter

import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Фабрика для адаптеров запросов.
 * Конвертирует возникающие по ходу запросов ошибки используя [errorConverter]
 */
class CallAdapterFactory(
    private val errorConverter: NetworkErrorConverter
) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *> {
        return ResultCallAdapter<Type>(returnType)
    }

    private inner class ResultCallAdapter<R> constructor(
        returnType: Type
    ) : CallAdapter<R, Any> {

        private val responseType: Type = if (returnType is ParameterizedType) {
            getParameterUpperBound(0, returnType)
        } else {
            returnType
        }

        override fun responseType(): Type {
            return responseType
        }

        override fun adapt(call: Call<R>): Any {
            return CallWrapper(call, errorConverter)
        }
    }
}