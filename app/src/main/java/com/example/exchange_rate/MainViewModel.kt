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


class MainViewModel : ViewModel() {
    private var _infoList = MutableLiveData<ExchangeRateInfo>()
    val infoList: LiveData<ExchangeRateInfo>
        get() = _infoList

    fun getData() {
        viewModelScope.launch {
            val iRetrofit = RetrofitClient.getClient()?.create(RetrofitService::class.java)

            val call = iRetrofit?.getExchangeRate(
                API.API_KEY,
                API.SOURCE
            ) //값이 없으면 return한다.있으면 it return {
            if (call != null) {
                call.enqueue(object : retrofit2.Callback<ExchangeRateInfo> {
                    override fun onResponse(
                        call: Call<ExchangeRateInfo>,
                        response: Response<ExchangeRateInfo>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                Log.i("minhxxk", "isSuccessful - Success $responseBody")
                                _infoList.value = responseBody!!
                            }
                        } else {
                            Log.i("minhxxk", "isSuccessful - Failure")
                        }
                    }

                    override fun onFailure(call: Call<ExchangeRateInfo>, t: Throwable) {
                        Log.i("minhxxk", "onFailure - Failure $t")
                    }
                })
            }
        }
    }
}