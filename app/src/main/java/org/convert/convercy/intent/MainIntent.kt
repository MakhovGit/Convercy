package org.convert.convercy.intent

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.convert.convercy.R
import org.convert.convercy.interactors.NetworkInteractor
import org.convert.convercy.model.info.DailyRatesInfo
import org.convert.convercy.model.intent.IntentContract
import org.convert.convercy.model.interactors.NetworkInteractorMessages
import org.convert.convercy.model.screens.ScreenEvents
import org.convert.convercy.model.screens.ScreenStates
import org.convert.convercy.utils.DataUtils
import kotlin.math.floor

class MainIntent(
    private val networkInteractor: NetworkInteractor
) : ViewModel(), IntentContract<ScreenStates, ScreenEvents> {
    private val _screenStateFlow: MutableStateFlow<ScreenStates> =
        MutableStateFlow(value = ScreenStates.SplashScreen)
    override val screenStateFlow = _screenStateFlow.asSharedFlow()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, error ->
        error.printStackTrace()
    }
    private val mainScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler)
    private lateinit var dailyRatesInfo: DailyRatesInfo
    private val lastExchangeScreenState = ScreenStates.ExchangeScreen(
        fromCurrencyType = "USD",
        fromCurrencyFlagResId = R.drawable.us,
        fromCurrencyAmount = "1",
        fromCurrencyList = listOf(),
        toCurrencyType = "RUB",
        toCurrencyFlagResId = R.drawable.ru,
        toCurrencyAmount = "",
        toCurrencyList  = listOf(),
        convertString = ""
    )
    private val dataUtils = DataUtils()
    init {
        prepareHandler()
    }
    private fun prepareHandler() {
        mainScope.launch {
            networkInteractor.outFlow
                .collect { result ->
                    when(result) {
                        is NetworkInteractorMessages.Loading -> {}
                        is NetworkInteractorMessages.Error -> {
                            _screenStateFlow.emit(ScreenStates.EventScreen)
                        }
                        is NetworkInteractorMessages.Success -> {
                            dailyRatesInfo = result.dailyRatesInfo
                            val currencyList = result.dailyRatesInfo.currency.map { dataUtils.addFlagResIdToCharCode(it.charCode) }
                            val currencyAmount = dataUtils.getCurrencyByCharCode(result.dailyRatesInfo.currency, "USD").value.toString()
                            with(lastExchangeScreenState) {
                                fromCurrencyList = currencyList
                                toCurrencyAmount = currencyAmount
                                toCurrencyList = currencyList
                                convertString = "1 USD = ${dataUtils.getCurrencyByCharCode(result.dailyRatesInfo.currency, "USD").value} RUB"
                            }
                            _screenStateFlow.emit(lastExchangeScreenState.copy())
                        }
                    }
                }
        }
    }

    override fun handleEvent() {
        mainScope.launch {
            _screenStateFlow.emit(ScreenStates.SplashScreen)
            networkInteractor.start()
        }
    }

    override fun handleEvent(screenEvent: ScreenEvents) {
        mainScope.launch {
            when (screenEvent) {
                is ScreenEvents.EventScreenReconnectEvent -> handleEvent()
                is ScreenEvents.ExchangeScreenFromCurrencyTypeChanged -> {
                    with(lastExchangeScreenState) {
                        fromCurrencyType = screenEvent.newFromCurrencyType
                        fromCurrencyFlagResId = dataUtils.getFlagResIdByCharCode(fromCurrencyType)
                        val fromCurrency = dataUtils.getCurrencyByCharCode(dailyRatesInfo.currency, fromCurrencyType)
                        val toCurrency = dataUtils.getCurrencyByCharCode(dailyRatesInfo.currency, toCurrencyType)
                        val toCurrencyAmountOne = floor((fromCurrency.value/fromCurrency.nominal/toCurrency.value) * 10000F) / 10000F
                        toCurrencyAmount = (toCurrencyAmountOne*fromCurrencyAmount.toFloat()).toString()
                        convertString = "1 ${fromCurrency.charCode} = $toCurrencyAmount ${toCurrency.charCode}"
                    }
                    _screenStateFlow.emit(lastExchangeScreenState.copy())
                }
                is ScreenEvents.ExchangeScreenToCurrencyTypeChanged -> {
                    with(lastExchangeScreenState) {
                        toCurrencyType = screenEvent.newToCurrencyType
                        toCurrencyFlagResId = dataUtils.getFlagResIdByCharCode(toCurrencyType)
                        val fromCurrency = dataUtils.getCurrencyByCharCode(dailyRatesInfo.currency, fromCurrencyType)
                        val toCurrency = dataUtils.getCurrencyByCharCode(dailyRatesInfo.currency, toCurrencyType)
                        val toCurrencyAmountOne = floor((fromCurrency.value/fromCurrency.nominal/toCurrency.value) * 10000F) / 10000F
                        toCurrencyAmount = (toCurrencyAmountOne*fromCurrencyAmount.toFloat()).toString()
                        convertString = "1 ${fromCurrency.charCode} = $toCurrencyAmount ${toCurrency.charCode}"
                    }
                    _screenStateFlow.emit(lastExchangeScreenState.copy())
                }
                is ScreenEvents.ExchangeScreenFromCurrencyAmountChanged -> {
                    with(lastExchangeScreenState) {
                        fromCurrencyAmount = screenEvent.newFromCurrencyAmount
                        val fromCurrency = dataUtils.getCurrencyByCharCode(dailyRatesInfo.currency, fromCurrencyType)
                        val toCurrency = dataUtils.getCurrencyByCharCode(dailyRatesInfo.currency, toCurrencyType)
                        val toCurrencyAmountOne = floor((fromCurrency.value/fromCurrency.nominal/toCurrency.value) * 10000F) / 10000F
                        toCurrencyAmount = (toCurrencyAmountOne*fromCurrencyAmount.toFloat()).toString()
                    }
                    _screenStateFlow.emit(lastExchangeScreenState.copy())
                }
            }
        }
    }


}