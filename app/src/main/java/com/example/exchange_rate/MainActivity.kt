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
    private lateinit var select: String
    var rate: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ViewModel 인스턴스 생성
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        onBinding()
        setListener()
        setObserve()

    }

    private fun onBinding() {
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
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> {  //한국(KRW)
                            viewModel.getData()
                            select = SELECT_COUNTRY.KRW
                            etRemittance.setText("")
                        }
                        1 -> {  //일본(JPY)
                            viewModel.getData()
                            select = SELECT_COUNTRY.JPY
                            etRemittance.setText("")
                        }
                        2 -> {  //필리핀(PHP)
                            viewModel.getData()
                            select = SELECT_COUNTRY.PHP
                            etRemittance.setText("")
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            //EditText 입력 변화 이벤트
            etRemittance.addTextChangedListener(object : TextWatcher {
                //입력하여 변화가 생기기전에 처리
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                //입력란의 변화와 동시에 처리
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s != null && s.toString() != "") {
                        if (Integer.parseInt(s.toString()) in 0..10000) {
                            tvResult.text =
                                "수취금액은 ${convertDecimalFormat(Integer.parseInt(s.toString()) * rate)} $select 입니다."
                        } else {
                            customToastView()
                        }
                    }
                }

                //입력이 끝났을 때 처리
                override fun afterTextChanged(s: Editable?) {}
            })
        }
    } //end of Listener

    private fun setObserve() {
        viewModel.infoList.observe(this, {
            when (select) {
                SELECT_COUNTRY.KRW -> {
                    binding.tvNation.text = "한국(KRW)"
                    rate = it.quotes.usdKrw
                    binding.tvExchangeRate.text = "${convertDecimalFormat(rate)} $select / USD"
                }
                SELECT_COUNTRY.JPY -> {
                    binding.tvNation.text = "일본(JPY)"
                    rate = it.quotes.usdJpy
                    binding.tvExchangeRate.text = "${convertDecimalFormat(rate)} $select / USD"
                }
                SELECT_COUNTRY.PHP -> {
                    binding.tvNation.text = "필리핀(PHP)"
                    rate = it.quotes.usdPhp
                    binding.tvExchangeRate.text = "${convertDecimalFormat(rate)} $select / USD"
                }
            }
            binding.tvInquiryTime.text = convertTimestampToDate(it.timestamp)
        })
    }

    //조회 시간 변환
    private fun convertTimestampToDate(timeStamp: Long): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm").format(timeStamp * 1000L)
    }

    private fun convertDecimalFormat(money: Float?): String {
        return DecimalFormat("#,###.##").format(money)
    }

    //CustomToast
    private fun customToastView() {
        val inflater = layoutInflater.inflate(R.layout.toast_board, null)
        var toast = Toast(this)
        toast.view = inflater
        toast.show()

    }

}