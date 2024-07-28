package org.convert.convercy.utils

import android.util.Log
import org.convert.convercy.model.info.CurrencyDescriptionInfo
import org.convert.convercy.settings.MAIN_LOG_TAG

class DataUtils {
    fun getCurrencyByCharCode(list: List<CurrencyDescriptionInfo>, charCode: String) =
        list.find { currencyDescriptionInfo -> currencyDescriptionInfo.charCode == charCode }
            ?: throw NoSuchElementException("No such element: $charCode!")

    fun roundTo3digits(number: Double) =
        Math.round(number * Double.THOUSAND) / Double.THOUSAND

    fun getToCurrencyAmountOne(
        fromNominal: Int,
        toNominal: Int,
        fromValue: Double,
        toValue: Double,
    ) = if (fromNominal >= Int.ZERO && toValue >= Int.ZERO && toNominal >= Int.ZERO)
        (fromValue / fromNominal / (toValue / toNominal))
    else Double.ZERO

    fun getParsedFromCurrencyAmount(
        toCurrencyAmountOne: Double,
        fromCurrencyAmount: String
    ): String {
        var toCurrencyAmountNew = Double.ONE
        try {
            toCurrencyAmountNew =
                roundTo3digits(toCurrencyAmountOne * fromCurrencyAmount.toDouble())
        } catch (e: Throwable) {
            Log.e(MAIN_LOG_TAG, "Error parsing $fromCurrencyAmount to Double!")
            e.printStackTrace()
        }
        return toCurrencyAmountNew.toBigDecimal().toPlainString()
    }

}