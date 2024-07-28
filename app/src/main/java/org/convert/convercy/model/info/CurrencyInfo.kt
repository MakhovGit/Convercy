package org.convert.convercy.model.info

import org.convert.convercy.R
import org.convert.convercy.utils.EMPTY

data class CurrencyInfo(
    val fromCurrencyType: String = "USD",
    val fromCurrencyFlagResId: Int = R.drawable.us,
    val fromCurrencyAmount: String = "1",
    val fromCurrencyList: List<Pair<String, Int>> = listOf(),
    val toCurrencyType: String = "RUB",
    val toCurrencyFlagResId: Int = R.drawable.ru,
    val toCurrencyAmount: String = String.EMPTY,
    val toCurrencyList: List<Pair<String, Int>> = listOf(),
    val convertString: String = String.EMPTY,
)