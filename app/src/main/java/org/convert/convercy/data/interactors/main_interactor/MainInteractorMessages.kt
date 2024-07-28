package org.convert.convercy.data.interactors.main_interactor

import org.convert.convercy.model.info.CurrencyInfo

sealed interface MainInteractorMessages {
    sealed class RequestDailyRates : MainInteractorMessages {
        data object Loading : RequestDailyRates()
        data class Error(val error: Throwable) : RequestDailyRates()
        data class Success(val currencyInfo: CurrencyInfo) : RequestDailyRates()
    }

    data class SwapCurrencies(
        val currencyInfo: CurrencyInfo
    ) : MainInteractorMessages

    data class FromCurrencyTypeChanged(
        val currencyInfo: CurrencyInfo
    ) : MainInteractorMessages

    data class FromCurrencyAmountChanged(
        val currencyInfo: CurrencyInfo
    ) : MainInteractorMessages

    data class ToCurrencyTypeChanged(
        val currencyInfo: CurrencyInfo
    ) : MainInteractorMessages
}