package com.example.exchange_rate

object API {

    const val API_KEY: String = "gnP1mtfFZIgoWh0sHjlQ09azzrGSd0jg"
    const val SOURCE: String = "USD"
    const val LIVE: String = "live"
}

enum class SELECT_COUNTRY(val eng:String,val kor:String){
    KRW("KRW","한국"),
    JPY("JPY","일본"),
    PHP("PHP","필리핀")
}