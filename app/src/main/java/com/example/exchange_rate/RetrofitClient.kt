package com.example.exchange_rate

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private var retrofitClient: Retrofit? = null

    fun getClient(): Retrofit? {
        val client = OkHttpClient.Builder()
        client.retryOnConnectionFailure(true)
        client.connectTimeout(10, TimeUnit.SECONDS)
        if (retrofitClient == null) {
            retrofitClient = Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
        }
        return retrofitClient
    }
}