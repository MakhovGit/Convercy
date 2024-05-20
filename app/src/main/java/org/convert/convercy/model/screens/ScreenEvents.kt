package org.convert.convercy.model.screens

sealed interface ScreenEvents {
    sealed interface  SplashScreenEvents {
        data object InitialEvent : SplashScreenEvents, ScreenEvents
    }
    sealed interface EventScreenEvents {
        data object ReconnectEvent : EventScreenEvents, ScreenEvents
    }
    sealed interface ExchangeScreenEvents {
        data object SwapCurrenciesEvent : ExchangeScreenEvents, ScreenEvents
        data class FromCurrencyTypeChangedEvent(
            val newFromCurrencyType: String
        ) : ExchangeScreenEvents, ScreenEvents

        data class ToCurrencyTypeChangedEvent(
            val newToCurrencyType: String
        ) : ExchangeScreenEvents, ScreenEvents

        data class FromCurrencyAmountChangedEvent(
            val newFromCurrencyAmount: String
        ) : ExchangeScreenEvents, ScreenEvents
    }
}
