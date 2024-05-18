package org.convert.convercy.utils

import org.convert.convercy.R
import org.convert.convercy.model.info.CurrencyDescriptionInfo

class DataUtils {
    fun getCurrencyByCharCode(list: List<CurrencyDescriptionInfo>, charCode: String) =
         list.find { it.charCode == charCode } ?: throw RuntimeException()

    fun getFlagResIdByCharCode(charCode: String) =
        when(charCode) {
            "AUD" -> R.drawable.az
            "AZN" -> R.drawable.au
            "GBP" -> R.drawable.gb
            "AMD" -> R.drawable.am
            "BYN" -> R.drawable.by
            "BGN" -> R.drawable.bg
            "BRL" -> R.drawable.br
            "HUF" -> R.drawable.hu
            "VND" -> R.drawable.vn
            "HKD" -> R.drawable.hk
            "GEL" -> R.drawable.ge
            "DKK" -> R.drawable.dk
            "AED" -> R.drawable.ae
            "USD" -> R.drawable.us
            "EUR" -> R.drawable.european_union
            "EGP" -> R.drawable.eg
            "INR" -> R.drawable.`in`
            "IDR" -> R.drawable.id
            "KZT" -> R.drawable.kz
            "CAD" -> R.drawable.ca
            "QAR" -> R.drawable.qa
            "KGS" -> R.drawable.kg
            "CNY" -> R.drawable.cn
            "MDL" -> R.drawable.md
            "NZD" -> R.drawable.nz
            "NOK" -> R.drawable.no
            "PLN" -> R.drawable.pl
            "RON" -> R.drawable.ro
            "RUB" -> R.drawable.ru
            "XDR" -> R.drawable.earth
            "SGD" -> R.drawable.sg
            "TJS" -> R.drawable.tj
            "THB" -> R.drawable.th
            "TRY" -> R.drawable.tr
            "TMT" -> R.drawable.tm
            "UZS" -> R.drawable.uz
            "UAH" -> R.drawable.ua
            "CZK" -> R.drawable.cz
            "SEK" -> R.drawable.se
            "CHF" -> R.drawable.ch
            "RSD" -> R.drawable.rs
            "ZAR" -> R.drawable.za
            "KRW" -> R.drawable.kr
            "JPY" -> R.drawable.jp
            else -> R.drawable.flag_placeholder
        }

    fun addFlagResIdToCharCode(charCode: String) = Pair(charCode, getFlagResIdByCharCode(charCode))
}