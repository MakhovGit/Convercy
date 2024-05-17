package org.convert.convercy.model.info

import org.convert.convercy.utils.EMPTY
import org.convert.convercy.utils.ZERO

data class CurrencyDescriptionInfo (
    val id: String = String.EMPTY,
    val numCode: Int = Int.ZERO,
    val charCode: String = String.EMPTY,
    val nominal: Int = Int.ZERO,
    val name: String = String.EMPTY,
    val value: Float = Float.ZERO,
    val previous: Float = Float.ZERO
)