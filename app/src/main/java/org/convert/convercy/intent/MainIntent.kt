package org.convert.convercy.intent

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.convert.convercy.data.keepers.DailyRatesKeeper
import org.convert.convercy.data.interactors.NetworkInteractor
import org.convert.convercy.intent.screen_handlers.EventScreenHandler
import org.convert.convercy.intent.screen_handlers.ExchangeScreenHandler
import org.convert.convercy.intent.screen_handlers.SplashScreenHandler
import org.convert.convercy.model.intent.IntentContract
import org.convert.convercy.model.interactors.NetworkInteractorMessages
import org.convert.convercy.model.screens.ScreenEvents
import org.convert.convercy.model.screens.ScreenStates
import org.convert.convercy.settings.MAIN_LOG_TAG

class MainIntent(
    private val networkInteractor: NetworkInteractor,
    dailyRatesKeeper: DailyRatesKeeper
) : ViewModel(), IntentContract<ScreenStates, ScreenEvents> {
    private val _screenStateFlow: MutableStateFlow<ScreenStates> =
        MutableStateFlow(value = ScreenStates.SplashScreenState)
    override val screenStateFlow = _screenStateFlow.asStateFlow()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, error ->
        Log.e(MAIN_LOG_TAG, "MainIntent error!: ${error.message}.")
        error.printStackTrace()
    }
    private val mainScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler)

    private val splashScreenHandler = SplashScreenHandler(networkInteractor)
    private val eventScreenHandler = EventScreenHandler(networkInteractor)
    private val exchangeScreenHandler = ExchangeScreenHandler(dailyRatesKeeper)
    init {
        networkInteractorHandlerStart()
        splashScreenHandlerStart()
        eventScreenHandlerStart()
        exchangeScreenHandlerStart()
        splashScreenHandler.handleEvent(ScreenEvents.SplashScreenEvents.InitialEvent)
    }

    private fun networkInteractorHandlerStart() {
        mainScope.launch {
            networkInteractor.outFlow.collect { result ->
                when(result) {
                    is NetworkInteractorMessages.Error -> {
                        _screenStateFlow.emit(ScreenStates.EventScreenState)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun splashScreenHandlerStart() {
        mainScope.launch {
            splashScreenHandler.outFlow
                .collect { screenState -> _screenStateFlow.emit(screenState) }
        }
    }

    private fun eventScreenHandlerStart() {
        mainScope.launch {
            eventScreenHandler.outFlow
                .collect { screenState -> _screenStateFlow.emit(screenState) }
        }
    }

    private fun exchangeScreenHandlerStart() {
        mainScope.launch {
            exchangeScreenHandler.outFlow
                .collect { screenState -> _screenStateFlow.emit(screenState) }
        }
    }

    override fun handleEvent(screenEvent: ScreenEvents) {
        mainScope.launch {
            when (screenEvent) {
                is ScreenEvents.SplashScreenEvents -> splashScreenHandler.handleEvent(screenEvent)
                is ScreenEvents.EventScreenEvents -> eventScreenHandler.handleEvent(screenEvent)
                is ScreenEvents.ExchangeScreenEvents -> exchangeScreenHandler.handleEvent(screenEvent)
            }
        }
    }
}