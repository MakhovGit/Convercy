package org.convert.convercy.data.dto

import com.google.gson.annotations.SerializedName

data class DailyRatesDTO (

    @field:SerializedName("Date")
    val date: String? = null,

    @field:SerializedName("PreviousDate")
    val previousDate: String? = null,

    @field:SerializedName("PreviousURL")
    val previousURL: String? = null,

    @field:SerializedName("Timestamp")
    val timestamp: String? = null,

    @field:SerializedName("Valute")
    val currency: CurrencyDTO? = null
)
