package ru.surfstudio.standard.f_splash

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.surfstudio.android.navigation.command.activity.Replace
import ru.surfstudio.android.navigation.executor.AppCommandExecutor
import ru.surfstudio.standard.base.flow.CompletableFlow
import ru.surfstudio.standard.base.flow.completableFlow
import ru.surfstudio.standard.i_initialization.InitializeAppInteractor
import ru.surfstudio.standard.ui.mvvm.view_model.BaseViewModel
import ru.surfstudio.standard.ui.navigation.MainRoute
import javax.inject.Inject

internal class SplashViewModel @Inject constructor(
        override val navCommandExecutor: AppCommandExecutor,
        private val initializeAppInteractor: InitializeAppInteractor
) : BaseViewModel(), ISplashViewModel {

    init {
        viewModelScope.launch {
            mergeInitDelay().collect {
                openNextScreen()
            }
        }
    }

    private fun mergeInitDelay(): CompletableFlow {
        val delayFlow = completableFlow {
            delay(TRANSITION_DELAY_MS)
        }
        val worker = initializeAppInteractor.initialize()
        return delayFlow.zip(worker){ _, _ ->  }.io().take(1)
    }

    private fun openNextScreen() {
        Replace(MainRoute()).execute()
    }

    companion object {

        /**
         * Минимальное время в миллисекундах, в течение которого показывается сплэш
         */
        const val TRANSITION_DELAY_MS = 2000L
    }
}

interface ISplashViewModel