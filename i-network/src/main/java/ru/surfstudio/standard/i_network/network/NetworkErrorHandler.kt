package ru.surfstudio.standard.i_network.network

import io.reactivex.exceptions.CompositeException
import ru.surfstudio.android.core.mvp.error.ErrorHandler
import ru.surfstudio.android.logger.Logger
import ru.surfstudio.standard.i_network.error.ConversionException
import ru.surfstudio.standard.i_network.error.HttpProtocolException
import ru.surfstudio.standard.i_network.error.NetworkException
import ru.surfstudio.standard.i_network.error.NoInternetException

/**
 * Базовый класс для обработки ошибок, возникающий при работе с Observable из слоя Interactor
 */
abstract class NetworkErrorHandler : ErrorHandler {

    override fun handleError(err: Throwable) {
        Logger.i(err, "NetworkErrorHandler handle error")
        when (err) {
            is CompositeException -> handleCompositeException(err)
            is ConversionException -> handleConversionError(err)
            is HttpProtocolException -> handleHttpProtocolException(err)
            is NoInternetException -> handleNoInternetError(err)
            else -> handleOtherError(err)
        }
    }

    /**
     * @param err - CompositeException может возникать при комбинировании Observable
     */
    private fun handleCompositeException(err: CompositeException) {
        val exceptions = err.exceptions
        var networkException: NetworkException? = null
        var otherException: Throwable? = null
        for (e in exceptions) {
            if (e is NetworkException) {
                if (networkException == null) {
                    networkException = e
                }
            } else if (otherException == null) {
                otherException = e
            }
        }
        if (networkException != null) {
            handleError(networkException)
        }
        if (otherException != null) {
            handleOtherError(otherException)
        }
    }

    protected abstract fun handleHttpProtocolException(e: HttpProtocolException)

    protected abstract fun handleNoInternetError(e: NoInternetException)

    protected abstract fun handleConversionError(e: ConversionException)

    protected abstract fun handleOtherError(e: Throwable)
}
