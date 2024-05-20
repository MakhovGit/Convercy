package org.convert.convercy.intent.screen_handlers

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.convert.convercy.data.keepers.DailyRatesKeeper
import org.convert.convercy.model.info.DailyRatesInfo
import org.convert.convercy.data.keepers.ExchangeScreenStateKeeper
import org.convert.convercy.model.screens.ScreenEvents
import org.convert.convercy.model.screens.ScreenStates
import org.convert.convercy.settings.MAIN_LOG_TAG
import org.convert.convercy.utils.ONE

class ExchangeScreenHandler(
    private val dailyRatesKeeper: DailyRatesKeeper
) {
    private val _outFlow: MutableSharedFlow<ScreenStates> =
        MutableSharedFlow(replay = Int.ONE)
    val outFlow = _outFlow.asSharedFlow()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, error ->
        Log.e(MAIN_LOG_TAG, "ExchangeScreenHandler error!: ${error.message}.")
        error.printStackTrace()
    }
    private val mainScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler)
    private lateinit var dailyRatesInfo: DailyRatesInfo
    private val exchangeScreenStateKeeper =
        ExchangeScreenStateKeeper(inputFlow = dailyRatesKeeper.outFlow)
    init {
       updateDailyRatesInfo()
       setExchangeScreenStateKeeperListener()
    }
    private fun updateDailyRatesInfo() {
        mainScope.launch {
            dailyRatesKeeper.outFlow
                .collect { externalDailyRatesInfo -> dailyRatesInfo = externalDailyRatesInfo }
        }
    }
    private fun setExchangeScreenStateKeeperListener() {
        mainScope.launch {
            exchangeScreenStateKeeper.outFlow.collect { screenState -> _outFlow.emit(screenState) }
        }
    }
    private fun onFromCurrencyTypeChangedEvent(
        screenEvent: ScreenEvents.ExchangeScreenEvents.FromCurrencyTypeChangedEvent
    ) {
        exchangeScreenStateKeeper.setFromCurrencyType(screenEvent.newFromCurrencyType)
    }
    private fun onFromCurrencyAmountChangedEvent(
        screenEvent: ScreenEvents.ExchangeScreenEvents.FromCurrencyAmountChangedEvent
    ) {
        exchangeScreenStateKeeper.setFromCurrencyAmount(screenEvent.newFromCurrencyAmount)
    }
    private fun onToCurrencyTypeChangedEvent(
        screenEvent: ScreenEvents.ExchangeScreenEvents.ToCurrencyTypeChangedEvent
    ) {
        exchangeScreenStateKeeper.setToCurrencyType(screenEvent.newToCurrencyType)
    }
    private fun onSwapCurrenciesEvent() {
        exchangeScreenStateKeeper.swapCurrencies()
    }
    fun handleEvent(screenEvent: ScreenEvents.ExchangeScreenEvents) {
        mainScope.launch {
            when(screenEvent) {
                is ScreenEvents.ExchangeScreenEvents.FromCurrencyTypeChangedEvent ->
                    onFromCurrencyTypeChangedEvent(screenEvent)
                is ScreenEvents.ExchangeScreenEvents.FromCurrencyAmountChangedEvent ->
                    onFromCurrencyAmountChangedEvent(screenEvent)
                is ScreenEvents.ExchangeScreenEvents.ToCurrencyTypeChangedEvent ->
                    onToCurrencyTypeChangedEvent(screenEvent)
                is ScreenEvents.ExchangeScreenEvents.SwapCurrenciesEvent ->
                    onSwapCurrenciesEvent()
            }
        }
    }

}