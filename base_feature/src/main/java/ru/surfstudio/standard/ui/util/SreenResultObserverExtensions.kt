package ru.surfstudio.standard.ui.util

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import ru.surfstudio.android.navigation.observer.ScreenResultObserver
import ru.surfstudio.android.navigation.observer.route.ResultRoute
import ru.surfstudio.android.navigation.route.BaseRoute
import java.io.Serializable

/**
 * Обертка над [ScreenResultObserver], позволяющая получать
 * результат асинхронно в виде [Flow]
 *
 * @param route роут, по которому будет возвращен резудльтат
 */
fun <T, R> ScreenResultObserver.resultFlowOf(
        route: R
): Flow<T> where T : Serializable, R : BaseRoute<*>, R : ResultRoute<T> {
    return callbackFlow<T> {
        addListener(route) { result: T ->
            sendBlocking(result)
        }
        awaitClose {
            removeListener(route)
        }
    }
}