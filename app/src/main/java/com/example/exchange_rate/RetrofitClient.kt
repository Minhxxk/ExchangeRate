package com.example.exchange_rate

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private var retrofitClient: Retrofit? = null

    fun getClient(): Retrofit? {
        val client = OkHttpClient.Builder()
        //실패시 다시시도 여부
        client.retryOnConnectionFailure(true)
        //커넥션 타임아웃
        client.connectTimeout(10, TimeUnit.SECONDS)
        if (retrofitClient == null) {
            //레트로핏 빌더를 통해 인스턴스 생성
            retrofitClient = Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                //위에서 설정한 클라이언트로 레트로핏 클라이언트를 설정한다.
                .client(client.build())
                .build()//레트로핏 빌드할때 .옵션 들을 추가하고 가장 마지막에 .build()로 마무리
        }
        return retrofitClient
    }




//    private val retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .client(OkHttpClient())
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    val service = retrofit.create(RetrofitService::class.java)

}