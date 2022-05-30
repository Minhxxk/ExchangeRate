package com.example.exchange_rate

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitService {
    @GET(API.LIVE)
    fun getExchangeRate(
        @Header("apikey") apiKey: String,
        @Query("source") source: String
    ): Call<ExchangeRateInfo>
}





