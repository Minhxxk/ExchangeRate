package com.example.exchange_rate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Half.toFloat
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.exchange_rate.databinding.ActivityMainBinding
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.text.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    var usdKrw: Float = 0.0f
    var usdJpy: Float = 0.0f
    var usdPhp: Float = 0.0f
    var timeStamp: Int = 0
    var resultKrwRemittance: Float? = null
    var resultJpyRemittance: Float? = null
    var resultPhpRemittance: Float? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //ViewModel 인스턴스 생성
        viewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)
        Binding()
        setObserve()
        setListener()

    }

    fun setListener() {
        binding.apply {
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> {  //한국(KRW)
                            viewModel.getData()
                            tvNation.text = "한국(KRW)"
                            tvExchangeRate.text = "${(String.format("%.2f", usdKrw))} KRW / USD"
                            tvInquiryTime.text = "${convertTimestampToDate(timeStamp.toLong())}"
                            tvResult.text = "수취금액은 $resultKrwRemittance KRW 입니다"
                            Toast.makeText(this@MainActivity, "한국 선택", Toast.LENGTH_SHORT).show()

                        }
                        1 -> {  //일본(JPY)
                            viewModel.getData()
                            tvNation.text = "일본(JPY)"
                            tvExchangeRate.text = "${(String.format("%.2f", usdJpy))} JPY / USD"
                            tvInquiryTime.text = "${convertTimestampToDate(timeStamp.toLong())}"
                            tvResult.text = "수취금액은 $resultKrwRemittance KRW 입니다"
                            Toast.makeText(this@MainActivity, "일본 선택", Toast.LENGTH_SHORT).show()
                        }
                        2 -> {  //필리핀(PHP)
                            viewModel.getData()
                            tvNation.text = "필리핀(PHP)"
                            tvExchangeRate.text = "${(String.format("%.2f", usdJpy))} JPY / USD"
                            tvInquiryTime.text = "${convertTimestampToDate(timeStamp.toLong())}"
                            tvResult.text = "수취금액은 $resultKrwRemittance KRW 입니다"
                            Toast.makeText(this@MainActivity, "필리핀 선택", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.i("minhxxk", "setListener() - onNotingSelected")
                }
            }
            etRemittance.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    Log.d("minhxxk", "beforeTextChanged() - $s")
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.d("minhxxk", "onTextChanged() - $s")
                }

                override fun afterTextChanged(s: Editable?) {
                    Log.d("minhxxk", "afterTextChanged() - $s")
                    if(s != null && s.toString() != ""){
                        var num: Int = Integer.parseInt(s.toString())
                        resultKrwRemittance = usdKrw * num
                        resultJpyRemittance = usdJpy * num
                        resultPhpRemittance = usdPhp * num
                    }
                }

            })
        }
    } //end of Listener

    fun setObserve() {
        viewModel.infoList.observe(this, androidx.lifecycle.Observer {
            usdKrw = it.quotes.usdKrw
            usdJpy = it.quotes.usdJpy
            usdPhp = it.quotes.usdPhp
            Log.d("minhxxk", "$usdKrw + $usdJpy + $usdPhp")
            var remittance = binding.etRemittance.text
            timeStamp = it.timestamp
            Log.i("minhxxk", "setObserve - $it")

        })
    }


    private fun Binding() {
        binding.spinner.adapter = ArrayAdapter.createFromResource(this, R.array.spinnerList, android.R.layout.simple_spinner_dropdown_item)
    }
    //조회 시간 변환
    fun convertTimestampToDate(timeStamp: Long): String {
        return SimpleDateFormat("yyyy-MM-dd hh:MM").format(timeStamp)
    }

    fun convertDecimalFormat(money: Float) {
        val df = DecimalFormat("#,###")
        df.format(money)
    }
}