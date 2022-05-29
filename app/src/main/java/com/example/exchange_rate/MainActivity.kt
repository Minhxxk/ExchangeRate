package com.example.exchange_rate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.exchange_rate.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //ViewModel 인스턴스 생성
        viewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)
        Binding()
        onListener()

    }

    fun onListener() {
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
                            Log.d("minhxxk", "onListener - success = ")
                            Toast.makeText(this@MainActivity, "한국 선택", Toast.LENGTH_SHORT).show()
//                            tvInquiryTime.text = CheckTime()
//                            tvExchangeRate.text = "${responseBody.quotes.usdKrw} KRW / USD"
                        }
                        1 -> {  //일본(JPY)
                            viewModel.getData()
                            tvNation.text = "일본(JPY)"
                            Toast.makeText(this@MainActivity, "일본 선택", Toast.LENGTH_SHORT).show()
//                            CheckTime()
//                            tvInquiryTime.text = "${CheckTime()}"
//                            tvExchangeRate.text = "${responseBody.quotes.usdJpy} JPY / USD"
                        }
                            2 -> {  //필리핀(PHP)
                            viewModel.getData()
                            Toast.makeText(this@MainActivity, "필리핀 선택", Toast.LENGTH_SHORT).show()
                            tvNation.text = "필리핀(PHP)"
//                            tvInquiryTime.text = "${CheckTime()}"
//                            tvExchangeRate.text = "${responseBody.quotes.usdPhp} PHP / USD"
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.i("minhxxk", "선택 안함")
                }
            }
        }
    } //end of Listener
    //뷰모델 - sucessData:ExchangeRateInfo
    //vm.sucessData.setObserve{
    //
    //
    // }
    //

//    fun setObserve() {
//        viewModel.infoList.observe(this, androidx.lifecycle.Observer {

//        })
//    }


    private fun Binding() {
        binding.spinner.adapter = ArrayAdapter.createFromResource(this, R.array.spinnerList, android.R.layout.simple_spinner_dropdown_item)
    }

}