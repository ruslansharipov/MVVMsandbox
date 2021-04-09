package ru.surfstudio.android.mvvmsandbox.network.call.adapter

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

/**
 * Класс-обертка над сетевым запросом.
 * При получении ошибки конвертирует эту ошибку при помощи [NetworkErrorConverter]
 */
internal class CallWrapper<T>(
        private val call: Call<T>,
        private val errorConverter: NetworkErrorConverter
) : Call<T> {

    override fun enqueue(callback: Callback<T>) {
        call.enqueue(object : Callback<T> {

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (call.isCanceled) {
                    // Иммитируется поведение OkHttp - если запрос был отменен - выбрасывается IOException
                    callback.onFailure(this@CallWrapper, IOException("Canceled"))
                } else {
                    callback.onResponse(this@CallWrapper, response)
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val error = errorConverter.convertNetworkError(t, call)
                callback.onFailure(this@CallWrapper, error)
            }
        })
    }

    override fun clone(): Call<T> = call.clone()

    override fun execute(): Response<T> = call.execute()

    override fun isExecuted(): Boolean = call.isExecuted

    override fun cancel() = call.cancel()

    override fun isCanceled(): Boolean = call.isCanceled

    override fun request(): Request = call.request()

    override fun timeout(): Timeout = call.timeout()

}