package br.com.deliverymuch.coupons

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class CouponRepository(
    private val context: Context
) {
    fun lodCupons(): List<Coupon>? {
        val jsonString = context.resources.openRawResource(R.raw.coupons)
            .bufferedReader().use { it.readText() }

        val gson: Gson = GsonBuilder().create()
        val itemType = object : TypeToken<List<Coupon>>() {}.type

        return gson.fromJson<List<Coupon>>(jsonString, itemType)
    }
}