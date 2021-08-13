package com.example.thirdproject

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CompoundButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.thirdproject.databinding.ItemListviewBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


val checkedPositions:ArrayList<Boolean> = arrayListOf(false,false,false,false,false,false,false,false,false,false,false,false,false)
class CustomAdapter(context: Context, private var dataList:ArrayList<Person>):BaseAdapter(){

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    lateinit var binding: ItemListviewBinding
    override fun getCount(): Int =dataList.size

    override fun getItem(position: Int): Any =dataList[position]

    override fun getItemId(position: Int): Long =position.toLong()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        binding = ItemListviewBinding.inflate(inflater,parent,false)

        var firstText:String = dataList[position].name
        binding.itemListviewFirst.text = firstText.slice(IntRange(0,0))
        binding.itemListviewTv.text = dataList[position].name
        binding.itemListviewTv2.text = dataList[position].number
        binding.itemListviewCheckbox.setChecked(dataList[position].checked2)
        if(dataList[position].checked){
            binding.itemListviewCallbtn.visibility = View.GONE
        }
        else if(!dataList[position].checked){
            binding.itemListviewCallbtn.visibility=View.VISIBLE
        }

        //눌림상태 저장 및 체크박스 클릭 시, 체크상태 로드
        binding.itemListviewCheckbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            checkedPositions[position] = b
        })
        binding.itemListviewCheckbox.setChecked(checkedPositions[position])
        dataList[position].checked2=checkedPositions[position]

        //전화버튼 누르면
        binding.itemListviewCallbtn.setOnClickListener(){

            //call버튼 클릭하면, MainActivity secondly로 position값 보내기
            val nameToHis:String = dataList[position].name
            val numToHis:String = dataList[position].number
            val now:LocalDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초")
            val formatted = now.format(formatter)
            var timeToHis:String = formatted
            MainActivity.secondly.add(History(nameToHis,numToHis,timeToHis))
            if (parent != null) {
                Toast.makeText(parent.context, "${nameToHis}에게 통화를 시작합니다.", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

}