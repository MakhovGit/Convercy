package org.convert.convercy.intent.screen_handlers

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.convert.convercy.data.interactors.NetworkInteractor
import org.convert.convercy.model.screens.ScreenEvents
import org.convert.convercy.model.screens.ScreenStates
import org.convert.convercy.settings.MAIN_LOG_TAG
import org.convert.convercy.utils.ONE

class SplashScreenHandler(
    private val networkInteractor: NetworkInteractor
) {
    private val _outFlow: MutableSharedFlow<ScreenStates> =
        MutableSharedFlow(replay = Int.ONE)
    val outFlow = _outFlow.asSharedFlow()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, error ->
        Log.e(MAIN_LOG_TAG, "SplashScreenHandler error!: ${error.message}.")
        error.printStackTrace()
    }
    private val mainScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler)

    private fun onInitialEvent() {
        mainScope.launch {
            _outFlow.emit(ScreenStates.SplashScreenState)
            networkInteractor.start()
        }
    }

    fun handleEvent(screenEvent: ScreenEvents.SplashScreenEvents) {
        mainScope.launch {
            when(screenEvent) {
                is ScreenEvents.SplashScreenEvents.InitialEvent -> onInitialEvent()
            }
        }
    }
}