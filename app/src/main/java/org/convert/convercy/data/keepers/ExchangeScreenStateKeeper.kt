package org.convert.convercy.data.keepers

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.convert.convercy.R
import org.convert.convercy.model.info.DailyRatesInfo
import org.convert.convercy.model.screens.ScreenStates
import org.convert.convercy.settings.MAIN_LOG_TAG
import org.convert.convercy.utils.DataUtils
import org.convert.convercy.utils.ONE
import org.convert.convercy.utils.ZERO

class ExchangeScreenStateKeeper(
    private val inputFlow: SharedFlow<DailyRatesInfo>
) {
    private val _outFlow: MutableSharedFlow<ScreenStates.ExchangeScreenState> =
        MutableSharedFlow(replay = Int.ONE)
    val outFlow = _outFlow.asSharedFlow()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, error ->
        Log.e(MAIN_LOG_TAG, "ExchangeScreenStateKeeper error!: ${error.message}.")
        error.printStackTrace()
    }
    private val mainScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler)
    private lateinit var dailyRatesInfo: DailyRatesInfo
    private lateinit var currencyList: List<Pair<String, Int>>
    private val dataUtils = DataUtils()
    private val currentScreenState = ScreenStates.ExchangeScreenState(
        fromCurrencyType = FROM_CURRENCY_TYPE_DEFAULT,
        fromCurrencyFlagResId = R.drawable.us,
        fromCurrencyAmount = FROM_CURRENCY_AMOUNT_DEFAULT,
        fromCurrencyList = listOf(),
        toCurrencyType = TO_CURRENCY_TYPE_DEFAULT,
        toCurrencyFlagResId = R.drawable.ru,
        toCurrencyAmount = TO_CURRENCY_AMOUNT_DEFAULT,
        toCurrencyList = listOf(),
        convertString = CONVERT_STRING_DEFAULT
    )
    init {
        updateDailyRates()
    }
    private fun updateDailyRates() {
        mainScope.launch {
            inputFlow.collect { newDailyRatesInfo ->
                dailyRatesInfo = newDailyRatesInfo
                currencyList =
                    dailyRatesInfo.currency.map { info ->  Pair(info.charCode, info.flagResId) }
                updateCurrentScreenState()
                _outFlow.emit(currentScreenState.copy())
            }
        }
    }
    private fun getFromCurrency() = dataUtils.getCurrencyByCharCode(
        dailyRatesInfo.currency,
        currentScreenState.fromCurrencyType
    )
    private fun getToCurrency() = dataUtils.getCurrencyByCharCode(
        dailyRatesInfo.currency,
        currentScreenState.toCurrencyType
    )

    private fun getParsedFromCurrencyAmount(
        toCurrencyAmountOne: Double,
        fromCurrencyAmount: String
    ): String {
        var toCurrencyAmountNew = Double.ONE
        try {
            toCurrencyAmountNew =
                dataUtils.roundTo3digits(toCurrencyAmountOne * fromCurrencyAmount.toDouble())
        } catch(e: Throwable) {
            Log.e(MAIN_LOG_TAG, "Error parsing $fromCurrencyAmount to Double!")
            e.printStackTrace()
        }
        return toCurrencyAmountNew.toString()
    }
    private fun getToCurrencyAmountOne(
        fromNominal: Int,
        toNominal: Int,
        fromValue: Double,
        toValue: Double,
    ) = if (fromNominal >= Int.ZERO && toValue >= Int.ZERO && toNominal >=Int.ZERO)
            (fromValue / fromNominal / (toValue / toNominal))
        else Double.ZERO
    private fun updateCurrentScreenState() {
        val fromCurrency = getFromCurrency()
        val toCurrency = getToCurrency()
        val toCurrencyAmountOne = getToCurrencyAmountOne(
            fromCurrency.nominal,
            toCurrency.nominal,
            fromCurrency.value,
            toCurrency.value
        )
        with (currentScreenState) {
            fromCurrencyFlagResId = fromCurrency.flagResId
            toCurrencyFlagResId = toCurrency.flagResId
            fromCurrencyList = currencyList
            toCurrencyList = currencyList
            toCurrencyAmount = getParsedFromCurrencyAmount(toCurrencyAmountOne, fromCurrencyAmount)
            convertString =
                "1 ${fromCurrency.charCode} = ${dataUtils.roundTo3digits(toCurrencyAmountOne)} ${toCurrency.charCode}"
        }
    }
    fun setFromCurrencyType(newCurrencyType: String) {
        mainScope.launch {
            currentScreenState.fromCurrencyType = newCurrencyType
            updateCurrentScreenState()
            _outFlow.emit(currentScreenState.copy())
        }
    }

    fun setFromCurrencyAmount(newCurrencyAmount: String) {
        mainScope.launch {
            currentScreenState.fromCurrencyAmount = newCurrencyAmount
            updateCurrentScreenState()
            _outFlow.emit(currentScreenState.copy())
        }
    }

    fun setToCurrencyType(newCurrencyType: String) {
        mainScope.launch {
            currentScreenState.toCurrencyType = newCurrencyType
            updateCurrentScreenState()
            _outFlow.emit(currentScreenState.copy())
        }
    }

    fun swapCurrencies() {
        mainScope.launch {
            with (currentScreenState) {
                val tempCurrencyType = fromCurrencyType
                fromCurrencyType = toCurrencyType
                toCurrencyType = tempCurrencyType
            }
            updateCurrentScreenState()
            _outFlow.emit(currentScreenState.copy())
        }
    }
    companion object {
        private const val FROM_CURRENCY_TYPE_DEFAULT = "USD"
        private const val FROM_CURRENCY_AMOUNT_DEFAULT = "1"
        private const val TO_CURRENCY_TYPE_DEFAULT = "RUB"
        private const val TO_CURRENCY_AMOUNT_DEFAULT = ""
        private const val CONVERT_STRING_DEFAULT = ""
    }
}