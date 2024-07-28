package org.convert.convercy.data.dto

import com.google.gson.annotations.SerializedName

data class CurrencyDescriptionDto(

    @field:SerializedName("ID")
    val id: String? = null,

    @field:SerializedName("NumCode")
    val numCode: Int? = null,

    @field:SerializedName("CharCode")
    val charCode: String? = null,

    @field:SerializedName("Nominal")
    val nominal: Int? = null,

    @field:SerializedName("Name")
    val name: String? = null,

    @field:SerializedName("Value")
    val currency: Double? = null,

    @field:SerializedName("Previous")
    val previous: Double? = null
)