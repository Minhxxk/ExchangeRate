package com.example.exchange_rate

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

const val API_KEY = "AlgYtpqGqRoneA1UhDc8EvEi42RDYWPs"
const val SOURCE = "USD"
class MainViewModel : ViewModel() {
    private var _infoList = MutableLiveData<ExchangeRateInfo>()
    val infoList: LiveData<ExchangeRateInfo>
    get() = _infoList
    //실패시
    private var _infoFailList = MutableLiveData<String>()
    val infoFailList: LiveData<String>
    get() = _infoFailList

    init {
        Log.d("minhxxk", "MainViewModel - init()")
    }

    fun getData(){
        viewModelScope.launch {
            val callGetLive = RetrofitClient.service.getExchangeRate(API_KEY, SOURCE)
            callGetLive.enqueue(object : retrofit2.Callback<ExchangeRateInfo> {
                override fun onResponse(
                    call: Call<ExchangeRateInfo>,
                    response: Response<ExchangeRateInfo>
                ) {
                    if(response.isSuccessful){
                        val responseBody = response.body()
                        if (responseBody != null) {
                            Log.i("minhxxk", "isSuccessful - Success $responseBody")
                            _infoList.value = responseBody!!
                            val success = responseBody.success.toString()
                            Log.d("minhxxk", "getData - $success")
                        }
                    } else{
                        Log.i("minhxxk", "isSuccessful - Failure")
                    }
                }

                override fun onFailure(call: Call<ExchangeRateInfo>, t: Throwable) {
                    Log.i("minhxxk", "onFailure - Failure")
                }
            })
        }
    }



}