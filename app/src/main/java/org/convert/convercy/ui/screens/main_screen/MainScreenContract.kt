package org.convert.convercy.ui.screens.main_screen

import org.convert.convercy.data.interactors.main_interactor.MainInteractorMessages
import org.convert.convercy.model.info.CurrencyInfo
import org.convert.convercy.ui.base.ViewEvent
import org.convert.convercy.ui.base.ViewState

class MainScreenContract {
    data class State(
        val isDataLoading: Boolean = true,
        val currencyInfo: CurrencyInfo = CurrencyInfo(),
        val error: MainInteractorMessages.RequestDailyRates.Error? = null
    ) : ViewState

    sealed class Event : ViewEvent {
        data object RequestDailyRates : Event()
        data object Reconnect : Event()
        data object SwapCurrencies : Event()
        data class FromCurrencyTypeChanged(
            val newFromCurrencyType: String
        ) : Event()

        data class ToCurrencyTypeChanged(
            val newToCurrencyType: String
        ) : Event()

        data class FromCurrencyAmountChanged(
            val newFromCurrencyAmount: String
        ) : Event()
    }

}