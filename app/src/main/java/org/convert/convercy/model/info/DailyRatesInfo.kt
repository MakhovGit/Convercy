package org.convert.convercy.model.info

import org.convert.convercy.utils.EMPTY

data class DailyRatesInfo(
    val date: String = String.EMPTY,
    val previousDate: String = String.EMPTY,
    val previousURL: String = String.EMPTY,
    val timestamp: String = String.EMPTY,
    val currency: List<CurrencyDescriptionInfo> = listOf()
)