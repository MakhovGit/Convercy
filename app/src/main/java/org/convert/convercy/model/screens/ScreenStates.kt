package org.convert.convercy.model.screens

sealed interface ScreenStates {
    interface SplashScreenState
    interface EventScreenState
    interface ExchangeScreenState
    data object SplashScreen : SplashScreenState, ScreenStates
    data object EventScreen : EventScreenState, ScreenStates
    data class ExchangeScreen(
        var fromCurrencyType: String,
        var fromCurrencyAmount: String,
        var fromCurrencyList: List<String>,
        var toCurrencyType: String,
        var toCurrencyAmount: String,
        var toCurrencyList: List<String>,
        var convertString: String
    ): ExchangeScreenState, ScreenStates

}