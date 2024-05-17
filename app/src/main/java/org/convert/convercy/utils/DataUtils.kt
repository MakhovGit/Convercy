package org.convert.convercy.utils

import org.convert.convercy.model.info.CurrencyDescriptionInfo

class DataUtils {
    fun getCurrencyByCharCode(list: List<CurrencyDescriptionInfo>, charCode: String) =
         list.find { it.charCode == charCode } ?: throw RuntimeException()
}