package com.alejandrorios.meli_challenge

import com.alejandrorios.meli_challenge.utils.getDiscount
import com.alejandrorios.meli_challenge.utils.toCurrencyString
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test


class ExtensionsTest  {

    @Test
    fun `When toCurrencyString is called with 3450983 then should return String formatted`() {
        val currency = 3450983

        currency.toCurrencyString() shouldBeEqualTo "$ 3.450.983"
    }

    @Test
    fun `When toCurrencyString is called with null then should return String 0`() {
        val currency = null

        currency.toCurrencyString() shouldBeEqualTo "$ 0"
    }

    @Test
    fun `When getDiscount is called with 42000 - 39900 then should return String with 5`() {
        val originalPrice = 42000
        val price = 39900

        originalPrice.getDiscount(price) shouldBeEqualTo "5"
    }

    @Test
    fun `When getDiscount is called with 0 - 39900 then should return String with 0`() {
        val originalPrice = 0
        val price = 39900

        originalPrice.getDiscount(price) shouldBeEqualTo "0"
    }
}
