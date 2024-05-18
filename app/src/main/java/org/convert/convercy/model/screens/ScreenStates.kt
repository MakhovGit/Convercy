package org.convert.convercy.model.screens

sealed interface ScreenStates {
    interface SplashScreenState
    interface EventScreenState
    interface ExchangeScreenState
    data object SplashScreen : SplashScreenState, ScreenStates
    data object EventScreen : EventScreenState, ScreenStates
    data class ExchangeScreen(
        var fromCurrencyType: String,
        var fromCurrencyFlagResId: Int,
        var fromCurrencyAmount: String,
        var fromCurrencyList: List<Pair<String, Int>>,
        var toCurrencyType: String,
        var toCurrencyFlagResId: Int,
        var toCurrencyAmount: String,
        var toCurrencyList: List<Pair<String, Int>>,
        var convertString: String
    ): ExchangeScreenState, ScreenStates

}