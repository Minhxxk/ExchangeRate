package com.example.exchange_rate

import com.google.gson.annotations.SerializedName

data class ExchangeRateInfo (
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("timestamp")
    val timestamp: Int,
    @SerializedName("source")
    val source: String,
    @SerializedName("quotes")
    val quotes: Quotes
)

data class Quotes(
    @SerializedName("USDKRW")
    val usdKrw: Float,
    @SerializedName("USDJPY")
    val usdJpy: Float,
    @SerializedName("USDPHP")
    val usdPhp: Float
)