package com.example.exchange_rate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.exchange_rate.databinding.ActivityMainBinding
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var select: SELECT_COUNTRY
    var rate: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        setSpinnerAdapter()
        setListener()
        setObserve()
    }

    private fun setSpinnerAdapter() {
        binding.spinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.spinnerList,
            android.R.layout.simple_spinner_dropdown_item
        )
    }

    private fun setListener() {
        binding.apply {
            //Spinner 아이템 클릭 시
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    etRemittance.text.clear()
                    viewModel.getData()
                    when (position) {
                        0 ->  select = SELECT_COUNTRY.KRW//한국(KRW)
                        1 ->  select = SELECT_COUNTRY.JPY//일본(JPY)
                        2 ->  select = SELECT_COUNTRY.PHP//필리핀(PHP)
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) { }
            }
            //EditText 입력 변화 이벤트
            etRemittance.addTextChangedListener(object : TextWatcher {
                //입력하여 변화가 생기기전에 처리
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
                //입력란의 변화와 동시에 처리
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s != null && s.toString() != "") {
                        if (Integer.parseInt(s.toString()) in 0..10000) {
                            var stringFormat = getString(R.string.edittext_input_succ)
                            var result=convertDecimalFormat(Integer.parseInt(s.toString()) * rate)
                            tvResult.text = String.format(stringFormat, result, select.eng)
                        } else {
                            Toast.makeText(this@MainActivity, R.string.edittext_input_err, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                //입력이 끝났을 때 처리
                override fun afterTextChanged(s: Editable?) { }
            })
        }
    } //end of Listener

    private fun setObserve() {
        binding.apply {
            viewModel.infoList.observe(this@MainActivity, {
                when (select) {
                    SELECT_COUNTRY.KRW -> {
                        tvNation.text = "${select.kor}(${select.eng})"
                        rate = it.quotes.usdKrw
                        tvExchangeRate.text = "${convertDecimalFormat(rate)} ${select.eng} / ${API.SOURCE}"
                    }
                    SELECT_COUNTRY.JPY -> {
                        tvNation.text = "${select.kor}(${select.eng})"
                        rate = it.quotes.usdJpy
                        tvExchangeRate.text = "${convertDecimalFormat(rate)} ${select.eng} / ${API.SOURCE}"
                    }
                    SELECT_COUNTRY.PHP -> {
                        tvNation.text = "${select.kor}(${select.eng})"
                        rate = it.quotes.usdPhp
                        tvExchangeRate.text = "${convertDecimalFormat(rate)} ${select.eng} / ${API.SOURCE}"
                    }
                }
                tvInquiryTime.text = convertTimestampToDate(it.timestamp)
            })
        }
    } //end of Listener

    //조회 시간 변환
    private fun convertTimestampToDate(timeStamp: Long): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm").format(timeStamp * 1000L)
    } //end of Listener

    //환율 및 수취금액 천단위 콤마, 소숫점 둘째 자리
    private fun convertDecimalFormat(money: Float?): String {
        return DecimalFormat("#,###.##").format(money)
    } //end of Listener
}