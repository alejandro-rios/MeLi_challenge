package com.alejandrorios.meli_challenge.utils

import java.text.DecimalFormat

fun Number?.toCurrencyString(): String {
    val format = DecimalFormat("\$ #,##0")
    return try {
        val formattedValue = format.format(this ?: 0)

        formattedValue.replace(",", ".")
    } catch (e: IllegalArgumentException) {
        format.format(0)
    }
}

fun Number.getDiscount(price: Number): String {
    if(price.toFloat() > this.toFloat()) return "0"
    val difference = this.toFloat().minus(price.toFloat())

    return ((difference/this.toFloat()) * 100).toInt().toString()
}


