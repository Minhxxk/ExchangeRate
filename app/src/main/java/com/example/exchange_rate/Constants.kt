package com.example.exchange_rate

object API {
    const val BASE_URL: String = "https://api.apilayer.com/currency_data/"

    const val SOURCE: String = "USD"
    const val LIVE: String = "live"
}

enum class SELECT_COUNTRY(val eng:String,val kor:String){
    KRW("KRW","한국"),
    JPY("JPY","일본"),
    PHP("PHP","필리핀")
}