package org.convert.convercy.model.screens

sealed interface ScreenStates {
    data object SplashScreenState : ScreenStates
    data object EventScreenState : ScreenStates
    data class ExchangeScreenState(
        var fromCurrencyType: String,
        var fromCurrencyFlagResId: Int,
        var fromCurrencyAmount: String,
        var fromCurrencyList: List<Pair<String, Int>>,
        var toCurrencyType: String,
        var toCurrencyFlagResId: Int,
        var toCurrencyAmount: String,
        var toCurrencyList: List<Pair<String, Int>>,
        var convertString: String
    ): ScreenStates

}