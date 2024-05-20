package org.convert.convercy.utils

import org.convert.convercy.model.info.CurrencyDescriptionInfo

class DataUtils {
    fun getCurrencyByCharCode(list: List<CurrencyDescriptionInfo>, charCode: String) =
         list.find { currencyDescriptionInfo ->  currencyDescriptionInfo.charCode == charCode }
             ?: throw NoSuchElementException("No such element: $charCode")

    fun roundTo3digits(number: Double) =
        Math.round(number * Double.THOUSAND)/Double.THOUSAND

}