package org.convert.convercy.data.interactors.main_interactor

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.convert.convercy.data.interactors.network_interactor.NetworkInteractor
import org.convert.convercy.data.interactors.network_interactor.NetworkInteractorMessages
import org.convert.convercy.model.info.CurrencyInfo
import org.convert.convercy.model.info.DailyRatesInfo
import org.convert.convercy.settings.MAIN_LOG_TAG
import org.convert.convercy.utils.DataUtils
import org.convert.convercy.utils.ONE

class MainInteractor(
    private val networkInteractor: NetworkInteractor,
    private val dataUtils: DataUtils
) {
    private val _outFlow: MutableSharedFlow<MainInteractorMessages> =
        MutableSharedFlow(replay = Int.ONE)
    val outFlow = _outFlow.asSharedFlow()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, error ->
        Log.e(MAIN_LOG_TAG, "Main interactor error! $error")
        error.printStackTrace()
    }
    private val mainScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler)
    private var requestDailyRatesJob: Job? = null
    private var networkInteractorHandlerJob: Job? = null
    private var onSwapCurrenciesJob: Job? = null
    private var onFromCurrencyTypeChangedJob: Job? = null
    private var onFromCurrencyAmountChangedJob: Job? = null
    private var onToCurrencyTypeChangedJob: Job? = null
    private lateinit var dailyRates: DailyRatesInfo
    private var currencyInfo: CurrencyInfo = CurrencyInfo()

    init {
        setNetworkInteractorHandler()
    }

    private fun updateCurrencyInfo() {
        val currencyList =
            dailyRates.currency.map { info ->
                Pair(
                    info.charCode,
                    info.flagResId
                )
            }
        val fromCurrency = dataUtils.getCurrencyByCharCode(
            dailyRates.currency,
            currencyInfo.fromCurrencyType
        )
        val toCurrency = dataUtils.getCurrencyByCharCode(
            dailyRates.currency,
            currencyInfo.toCurrencyType
        )
        val toCurrencyAmountOne = dataUtils.getToCurrencyAmountOne(
            fromCurrency.nominal,
            toCurrency.nominal,
            fromCurrency.value,
            toCurrency.value
        )
        currencyInfo = currencyInfo.copy(
            fromCurrencyFlagResId = fromCurrency.flagResId,
            toCurrencyFlagResId = toCurrency.flagResId,
            fromCurrencyList = currencyList,
            toCurrencyList = currencyList,
            toCurrencyAmount = dataUtils.getParsedFromCurrencyAmount(
                toCurrencyAmountOne,
                currencyInfo.fromCurrencyAmount
            ),
            convertString =
            "1 ${fromCurrency.charCode} = ${dataUtils.roundTo3digits(toCurrencyAmountOne)} ${toCurrency.charCode}"
        )
    }

    private fun setNetworkInteractorHandler() {
        networkInteractorHandlerJob?.cancel()
        networkInteractorHandlerJob = mainScope.launch {
            networkInteractor.outFlow.collect { message ->
                if (message is NetworkInteractorMessages.RequestDailyRates.Success) {
                    dailyRates = message.dailyRatesInfo
                    updateCurrencyInfo()
                    _outFlow.emit(
                        MainInteractorMessages.RequestDailyRates.Success(
                            currencyInfo = currencyInfo
                        )
                    )
                }
                if (message is NetworkInteractorMessages.RequestDailyRates.Error) {
                    _outFlow.emit(
                        MainInteractorMessages.RequestDailyRates.Error(
                            error = message.error
                        )
                    )
                }
            }
        }
    }

    fun onSwapCurrenciesEvent() {
        onSwapCurrenciesJob?.cancel()
        onSwapCurrenciesJob = mainScope.launch {
            val tempCurrencyType = currencyInfo.fromCurrencyType
            currencyInfo = currencyInfo.copy(
                fromCurrencyType = currencyInfo.toCurrencyType,
                toCurrencyType = tempCurrencyType
            )
            updateCurrencyInfo()
            _outFlow.emit(
                MainInteractorMessages.SwapCurrencies(
                    currencyInfo = currencyInfo
                )
            )
        }
    }

    fun onFromCurrencyTypeChangedEvent(newFromCurrencyType: String) {
        onFromCurrencyTypeChangedJob?.cancel()
        onFromCurrencyTypeChangedJob = mainScope.launch {
            currencyInfo = currencyInfo.copy(
                fromCurrencyType = newFromCurrencyType
            )
            updateCurrencyInfo()
            _outFlow.emit(
                MainInteractorMessages.FromCurrencyTypeChanged(
                    currencyInfo = currencyInfo
                )
            )
        }
    }

    fun onFromCurrencyAmountChangedEvent(newFromCurrencyAmount: String) {
        onFromCurrencyAmountChangedJob?.cancel()
        onFromCurrencyAmountChangedJob = mainScope.launch {
            currencyInfo = currencyInfo.copy(
                fromCurrencyAmount = newFromCurrencyAmount
            )
            updateCurrencyInfo()
            _outFlow.emit(
                MainInteractorMessages.FromCurrencyAmountChanged(
                    currencyInfo = currencyInfo
                )
            )
        }
    }

    fun onToCurrencyTypeChangedEvent(newToCurrencyType: String) {
        onToCurrencyTypeChangedJob?.cancel()
        onToCurrencyTypeChangedJob = mainScope.launch {
            currencyInfo = currencyInfo.copy(
                toCurrencyType = newToCurrencyType
            )
            updateCurrencyInfo()
            _outFlow.emit(
                MainInteractorMessages.ToCurrencyTypeChanged(
                    currencyInfo = currencyInfo
                )
            )
        }
    }

    fun requestDailyRates() {
        requestDailyRatesJob?.cancel()
        requestDailyRatesJob = mainScope.launch {
            _outFlow.emit(MainInteractorMessages.RequestDailyRates.Loading)
            networkInteractor.requestDailyRates()
        }
    }
}