package com.example.exchange_rate

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url

interface RetrofitService {
    @GET("live")
    fun getExchangeRate(
        @Header("apikey") apiKey: String,
        @Query("source") source: String
    ): Call<ExchangeRateInfo>
}





