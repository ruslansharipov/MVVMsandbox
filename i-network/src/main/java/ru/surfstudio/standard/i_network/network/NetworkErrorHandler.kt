package ru.surfstudio.standard.i_network.network

import ru.surfstudio.android.core.mvp.error.ErrorHandler
import ru.surfstudio.android.logger.Logger
import ru.surfstudio.standard.i_network.error.ConversionException
import ru.surfstudio.standard.i_network.error.HttpProtocolException
import ru.surfstudio.standard.i_network.error.NoInternetException

/**
 * Базовый класс для обработки ошибок, возникающий при работе с Observable из слоя Interactor
 */
abstract class NetworkErrorHandler : ErrorHandler {

    override fun handleError(err: Throwable) {
        Logger.i(err, "NetworkErrorHandler handle error")
        when (err) {
            is ConversionException -> handleConversionError(err)
            is HttpProtocolException -> handleHttpProtocolException(err)
            is NoInternetException -> handleNoInternetError(err)
            else -> handleOtherError(err)
        }
    }

    protected abstract fun handleHttpProtocolException(e: HttpProtocolException)

    protected abstract fun handleNoInternetError(e: NoInternetException)

    protected abstract fun handleConversionError(e: ConversionException)

    protected abstract fun handleOtherError(e: Throwable)
}
