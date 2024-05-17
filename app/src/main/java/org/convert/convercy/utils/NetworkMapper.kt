package org.convert.convercy.utils

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
            value = currencyDescriptionDTO.currency ?: Float.ZERO,
            previous = currencyDescriptionDTO.previous ?: Float.ZERO
        )
    fun map(dailyRatesDTO: DailyRatesDTO): DailyRatesInfo {
        val currencyList: MutableList<CurrencyDescriptionInfo> = mutableListOf()
        val rubDescriptionInfo = CurrencyDescriptionInfo(
            numCode = 643,
            charCode = "RUB",
            nominal = 1,
            name = "Российский рубль",
            value = 1.0F,
            previous = 1.0F
        )
        currencyList.add(rubDescriptionInfo)
        dailyRatesDTO.currency?.aud?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.azn?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.gbp?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.amd?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.byn?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.bgn?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.brl?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.huf?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.vnd?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.hkd?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.gel?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.dkk?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.aed?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.usd?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.eur?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.egp?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.inr?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.idr?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.kzt?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.cad?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.qar?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.kgs?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.cny?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.mdl?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.nzd?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.nok?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.pln?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.ron?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.xdr?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.sgd?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.tjs?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.thb?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.trY?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.tmt?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.uzs?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.uah?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.czk?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.sek?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.chf?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.rsd?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.zar?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.krw?.let { description -> currencyList.add(map(description)) }
        dailyRatesDTO.currency?.jpy?.let { description -> currencyList.add(map(description)) }
        return DailyRatesInfo(
            date = dailyRatesDTO.date ?: String.EMPTY,
            previousDate = dailyRatesDTO.previousDate ?: String.EMPTY,
            previousURL = dailyRatesDTO.previousURL ?: String.EMPTY,
            timestamp = dailyRatesDTO.timestamp ?: String.EMPTY,
            currency = currencyList
        )
    }
}