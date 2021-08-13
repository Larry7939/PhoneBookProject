package com.example.thirdproject

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import com.example.thirdproject.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var userinput = binding.inputNameSecond.text.toString()
        if(userinput.length<=0) {
            binding.storeSecond.setTextColor(Color.parseColor("#5C5C5C"))
            binding.countTextviewSecond.setTextColor(Color.parseColor("#FF0000"))
            binding.countTextviewSecond.text="이름은 한 글자 이상 입력해주세요!"
        }
        binding.cancelSecond.setOnClickListener{
            finish()
        }
        binding.storeSecond.setOnClickListener{
            var userinput = binding.inputNameSecond.text.toString()
            if(userinput.length>0) {
                var inserted1 = binding.inputNameSecond.text.toString()
                var inserted2 = binding.inputPhonenumSecond.text.toString()
                val intent = Intent(this, MainActivity::class.java)
                if (inserted1 != null && inserted2 != null) {
                    intent.putExtra("added_name", inserted1)
                    intent.putExtra("added_number", inserted2)
                    startActivity(intent)
                }
            }
        }
        binding.inputNameSecond.addTextChangedListener(
            object: TextWatcher {

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    binding.countTextviewSecond.text= "0 / 100"
                    var userinput = binding.inputNameSecond.text.toString()
                    if(userinput.length<=0){
                        binding.countTextviewSecond.text="이름은 한 글자 이상 입력해주세요!"
                        binding.countTextviewSecond.setTextColor(Color.parseColor("#FF0000"))
                        binding.storeSecond.setClickable(false)
                        binding.storeSecond.setTextColor(Color.parseColor("#5C5C5C"))
                    }
                    else{
                        binding.storeSecond.setTextColor(Color.parseColor("#6184DD"))
                        binding.countTextviewSecond.setTextColor(Color.parseColor("#5C5C5C"))
                        binding.storeSecond.setClickable(true)
                    }
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    var userinput = binding.inputNameSecond.text.toString()
                    binding.countTextviewSecond.text = userinput.length.toString() + " / 100"
                    if(userinput.length<=0){
                        binding.countTextviewSecond.text="이름은 한 글자 이상 입력해주세요!"
                        binding.countTextviewSecond.setTextColor(Color.parseColor("#FF0000"))
                        binding.storeSecond.setTextColor(Color.parseColor("#5C5C5C"))
                        binding.storeSecond.setClickable(false)
                    }
                    else{
                        binding.storeSecond.setTextColor(Color.parseColor("#6184DD"))
                        binding.countTextviewSecond.setTextColor(Color.parseColor("#5C5C5C"))
                        binding.storeSecond.setClickable(true)
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    var userinput = binding.inputNameSecond.text.toString()
                    binding.countTextviewSecond.text = userinput.length.toString() + " / 100"
                    if(userinput.length<=0){
                        binding.countTextviewSecond.text="이름은 한 글자 이상 입력해주세요!"
                        binding.countTextviewSecond.setTextColor(Color.parseColor("#FF0000"))
                        binding.storeSecond.setTextColor(Color.parseColor("#ABABAB"))
                        binding.storeSecond.setClickable(false)
                    }
                    else{
                        binding.storeSecond.setTextColor(Color.parseColor("#6184DD"))
                        binding.countTextviewSecond.setTextColor(Color.parseColor("#5C5C5C"))
                        binding.storeSecond.setClickable(true)
                    }
                }
            }
        )
    }
}