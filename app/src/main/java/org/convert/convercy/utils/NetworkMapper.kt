package org.convert.convercy.utils

import org.convert.convercy.R
import org.convert.convercy.data.dto.CurrencyDescriptionDTO
import org.convert.convercy.data.dto.DailyRatesDTO
import org.convert.convercy.model.info.CurrencyDescriptionInfo
import org.convert.convercy.model.info.DailyRatesInfo

class NetworkMapper {
    private fun map(currencyDescriptionDTO: CurrencyDescriptionDTO) =
        CurrencyDescriptionInfo(
            id = currencyDescriptionDTO.id ?: String.EMPTY,
            numCode = currencyDescriptionDTO.numCode ?: Int.ZERO,
            charCode = currencyDescriptionDTO.charCode ?: String.EMPTY,
            nominal = currencyDescriptionDTO.nominal ?: Int.ZERO,
            name = currencyDescriptionDTO.name ?: String.EMPTY,
            value = currencyDescriptionDTO.currency ?: Double.ZERO,
            previous = currencyDescriptionDTO.previous ?: Double.ZERO
        )
    fun map(dailyRatesDTO: DailyRatesDTO): DailyRatesInfo {
        val currencyList: MutableList<CurrencyDescriptionInfo> = mutableListOf()
        val rubDescriptionInfo = CurrencyDescriptionInfo(
            numCode = RUB_NUM_CODE,
            charCode = RUB_CHAR_CODE,
            flagResId = R.drawable.ru,
            nominal = RUB_NOMINAL,
            name = RUB_NAME,
            value = RUB_VALUE,
            previous = RUB_PREVIOUS
        )
        currencyList.add(rubDescriptionInfo)
        dailyRatesDTO.currency?.aud?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.au))
        }
        dailyRatesDTO.currency?.azn?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.az))
        }
        dailyRatesDTO.currency?.gbp?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.gb))
        }
        dailyRatesDTO.currency?.amd?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.am))
        }
        dailyRatesDTO.currency?.byn?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.by))
        }
        dailyRatesDTO.currency?.bgn?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.bg))
        }
        dailyRatesDTO.currency?.brl?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.br))
        }
        dailyRatesDTO.currency?.huf?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.hu))
        }
        dailyRatesDTO.currency?.vnd?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.vn))
        }
        dailyRatesDTO.currency?.hkd?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.hk))
        }
        dailyRatesDTO.currency?.gel?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.ge))
        }
        dailyRatesDTO.currency?.dkk?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.dk))
        }
        dailyRatesDTO.currency?.aed?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.ae))
        }
        dailyRatesDTO.currency?.usd?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.us))
        }
        dailyRatesDTO.currency?.eur?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.european_union))
        }
        dailyRatesDTO.currency?.egp?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.eg))
        }
        dailyRatesDTO.currency?.inr?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.`in`))
        }
        dailyRatesDTO.currency?.idr?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.id))
        }
        dailyRatesDTO.currency?.kzt?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.kz))
        }
        dailyRatesDTO.currency?.cad?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.ca))
        }
        dailyRatesDTO.currency?.qar?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.qa))
        }
        dailyRatesDTO.currency?.kgs?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.kg))
        }
        dailyRatesDTO.currency?.cny?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.cn))
        }
        dailyRatesDTO.currency?.mdl?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.md))
        }
        dailyRatesDTO.currency?.nzd?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.nz))
        }
        dailyRatesDTO.currency?.nok?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.no))
        }
        dailyRatesDTO.currency?.pln?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.pl))
        }
        dailyRatesDTO.currency?.ron?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.ro))
        }
        dailyRatesDTO.currency?.xdr?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.earth))
        }
        dailyRatesDTO.currency?.sgd?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.sg))
        }
        dailyRatesDTO.currency?.tjs?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.tj))
        }
        dailyRatesDTO.currency?.thb?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.th))
        }
        dailyRatesDTO.currency?.trY?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.tr))
        }
        dailyRatesDTO.currency?.tmt?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.tm))
        }
        dailyRatesDTO.currency?.uzs?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.uz))
        }
        dailyRatesDTO.currency?.uah?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.ua))
        }
        dailyRatesDTO.currency?.czk?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.cz))
        }
        dailyRatesDTO.currency?.sek?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.se))
        }
        dailyRatesDTO.currency?.chf?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.ch))
        }
        dailyRatesDTO.currency?.rsd?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.rs))
        }
        dailyRatesDTO.currency?.zar?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.za))
        }
        dailyRatesDTO.currency?.krw?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.kr))
        }
        dailyRatesDTO.currency?.jpy?.let {
            description -> currencyList.add(map(description).copy(flagResId = R.drawable.jp))
        }
        return DailyRatesInfo(
            date = dailyRatesDTO.date ?: String.EMPTY,
            previousDate = dailyRatesDTO.previousDate ?: String.EMPTY,
            previousURL = dailyRatesDTO.previousURL ?: String.EMPTY,
            timestamp = dailyRatesDTO.timestamp ?: String.EMPTY,
            currency = currencyList
        )
    }

    companion object {
        private const val RUB_NUM_CODE = 643
        private const val RUB_CHAR_CODE = "RUB"
        private const val RUB_NOMINAL = 1
        private const val RUB_NAME = "Российский рубль"
        private const val RUB_VALUE = 1.0
        private const val RUB_PREVIOUS = 1.0
    }
}