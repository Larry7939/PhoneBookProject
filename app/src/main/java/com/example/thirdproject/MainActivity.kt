package com.example.thirdproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.thirdproject.databinding.ActivityMainBinding
import org.json.JSONArray
import java.util.*
import kotlin.collections.ArrayList

data class Person(val name:String,val number:String,var checked:Boolean=false,var checked2:Boolean=false)
class MainActivity : AppCompatActivity() {

companion object PersonList {
    var personlist = ArrayList<Person>()
    var secondly = ArrayList<History>()
}
    var displayList = ArrayList<Person>()


private lateinit var customAdapter:CustomAdapter
private lateinit var binding:ActivityMainBinding

override fun onCreate(savedInstanceState: Bundle?) {
    //1.반복되는 틀(아이템 레이아웃)
    //2.데이터(ArrayList or mutableList)
    //3.어댑터 클래스
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    customAdapter = CustomAdapter(this, personlist)
    binding.listviewMain.adapter = customAdapter

    binding.listviewMain.choiceMode = ListView.CHOICE_MODE_MULTIPLE


    //최근 기록 액티비티 전환
    binding.btSale.setOnClickListener{
        val intent = Intent(this, HistoryActivity::class.java)
        if(!secondly.isEmpty()) {
            intent.putExtra("called_list", secondly)
        }
        startActivity(intent)
        secondly.clear() //다음에 보낼 때 중복되지 않도록 clear
    }
    //연락처 추가
    binding.addAddrBtn.setOnClickListener {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }
    //연락처 삭제
    binding.addRemovBtn.setOnClickListener {
        //삭제버튼 눌림취소되면 추가버튼은 생기고 휴지통 버튼 사라지게
        if (binding.addRemovBtn.isSelected && personlist.size > 0) {
            binding.addRemovBtn.isSelected = false
            binding.addAddrBtn.visibility = View.VISIBLE
            binding.deleteBtn.visibility = View.GONE
            binding.addAddrBtn.isClickable = true
            customAdapter.binding.itemListviewCallbtn.visibility = View.VISIBLE
            //삭제 버튼 클릭취소시 어댑터에서 아이템 개수 가져와서 checked를 false로 변경해버리고 갱신
            var cnt = customAdapter.count
            for (i in 0 until cnt) {
                personlist.get(i).checked = false
                customAdapter.notifyDataSetChanged()
            }
        }
        //삭제버튼 눌리면 추가버튼은 사라지고 휴지통 버튼 나타나게
        else if (!binding.addRemovBtn.isSelected && personlist.size > 0) {
            binding.addRemovBtn.isSelected = true
            binding.addAddrBtn.visibility = View.GONE
            binding.addAddrBtn.isClickable = false
            binding.deleteBtn.visibility = View.VISIBLE
            //삭제 버튼 클릭시 어댑터에서 아이템 개수 가져와서 checked를 true로 변경해버리고 갱신

            var cnt = customAdapter.count
            for (i in 0 until cnt) {
                personlist.get(i).checked = true
            }
            customAdapter.notifyDataSetChanged()
        } else {
            Toast.makeText(this, "삭제할 연락처가 없습니다!", Toast.LENGTH_SHORT).show()
        }
    }

    //연락처 삭제(휴지통)
    //휴지통 버튼 누르면 휴지통 버튼 사라지고 추가버튼 생기게 구성
    binding.deleteBtn.setOnClickListener{
        //버튼들 visibility정리
        binding.addAddrBtn.isClickable = true
        binding.addRemovBtn.isSelected = false
        binding.addAddrBtn.visibility = View.VISIBLE
        binding.deleteBtn.visibility = View.GONE
        customAdapter.binding.itemListviewCallbtn.visibility = View.VISIBLE
        //삭제 버튼 클릭 시  가져와서 checked를 false로 변경해버리고 갱신
        var cnt = customAdapter.count
        for (i in 0 until cnt) {
            personlist.get(i).checked = false
        }
        customAdapter.notifyDataSetChanged()
        //체크된 아이템들의 idx와 check여부
        //무조건 끝에서부터 삭제해야함. 안그러면, 맨 끝 아이템과 다중 선택 삭제 할 시,
        //앞에걸 먼저 삭제해버리면 앞으로 땡겨오고, 결국 빈 자리에 removeAt을 하게 됨.
        for(i in checkedPositions.size-1 downTo 0){
            if(checkedPositions[i]){
                checkedPositions[i]=false
                personlist.removeAt(i)
            }
        }
//
//        for((i,v) in checkedPositions.withIndex()){
//            if(v)
//            {
//                checkedPositions[i]=false
//                Toast.makeText(this, "체크된 item : ${i}번째", Toast.LENGTH_SHORT).show()
//                personlist.removeAt(i)
//            }
//        }
        customAdapter.notifyDataSetChanged()

        binding.listviewMain.clearChoices()
    }




    var name: String? = intent.getStringExtra("added_name")
    var phonenum: String? = intent.getStringExtra("added_number")
    if (name != null && phonenum != null) {
//        Toast.makeText(this, "연락처 저장 성공 ${name} , ${phonenum}", Toast.LENGTH_SHORT).show()
        personlist.add(Person(name, phonenum, false))
        customAdapter.notifyDataSetChanged()
    }
    //연락처가 없어요!
    if (personlist.size <= 0) {
        binding.centerTvMain.text = "추가된 연락처가 없습니다!\n   연락처를 추가해보세요!"
    } else if (personlist.size > 0) {
        binding.centerTvMain.text = ""
    }

    //CustomAdapter에서 버튼 누르면 secondly배열에 저장됨.
//    for (i in secondly) {
        //어댑터에서 다른 곳으로 바로 보내면 될 듯? 일단 통화기록 액티비티랑 어댑터, 아이템 레이아웃 부터 만들자.
            //다 만들었고, 이제 이걸 배열에 secondly배열에 저장해놨다가, 최근기록 버튼을 누름과 동시에 intent로 묶어서 보내면 된다.
                //이름,번호, 그리고 누른 시각
                    //어댑터에서 바로 intent로 보낼까?? -> X
        //제대로 secondly에 안착했고, 최근기록 버튼 누름과 동시에 intent로 보내자.
        //다음번에 또 보낼 때 중복되지 않도록, onCreate에서 secondly는 clear하자.

//    }
    //personlist의 내용 displayList에 복사 = 말고 addAll로 해야함!!!
    //search용 EditText에 addTextChangedListener를 달고 object:TextWatcher라는 인터페이스 사용.
    //아래에 override되어있는 함수들이 TextWatcher의 내부 메소드임!
    displayList.addAll(personlist)

    binding.searchviewMain.addTextChangedListener(object:TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            var searchText:String = binding.searchviewMain.text.toString()
            //어댑터에 정의해놓은 fillter에 searchText를 집어넣음.
            fillter(searchText)
        }
    })

    }
    //MainActivity에서 호출할 함수 fillter
    fun fillter(searchText:String){
        //매번 입력할 때마다 clear해준다.
        personlist.clear()
        //displayList에 백업해놓고, personlist에 계속 추가하는 방식임.
        if(searchText.length==0){
            personlist.addAll(displayList)
        }
        else{
            Toast.makeText(this, "fillter함수 진입", Toast.LENGTH_SHORT).show()
            //실제 데이터가 들어있는 dataList를 뒤져서 searchText를 포함한 item을 검색 시 보여주기용 리스트인 displayListItem에 add
            for(i in 0 until displayList.size){
                if(displayList[i].name.contains(searchText)){
                    personlist.add(displayList[i])
                    Toast.makeText(this, "contains여부 검사", Toast.LENGTH_SHORT).show()
                }
            }
        }
        customAdapter.notifyDataSetChanged()
    }
}


