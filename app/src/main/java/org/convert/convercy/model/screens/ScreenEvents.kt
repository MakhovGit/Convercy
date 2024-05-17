package org.convert.convercy.model.screens

interface ScreenEvents {
    data object EventScreenReconnectEvent : ScreenEvents
    data class ExchangeScreenFromCurrencyTypeChanged(
        val newFromCurrencyType: String
    ): ScreenEvents
    data class ExchangeScreenToCurrencyTypeChanged(
        val newToCurrencyType: String
    ): ScreenEvents
    data class ExchangeScreenFromCurrencyAmountChanged(
        val newFromCurrencyAmount: String
    ): ScreenEvents
}