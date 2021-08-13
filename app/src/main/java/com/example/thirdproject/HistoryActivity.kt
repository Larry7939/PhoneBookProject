package com.example.thirdproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thirdproject.databinding.ActivityHistoryBinding
import java.io.Serializable

data class History(var name:String,var number:String,var time:String):Serializable

class HistoryActivity : AppCompatActivity() {

companion object PersonList {
    var hislist = ArrayList<History>()
    var data:MutableList<History> = mutableListOf()
}
    //activity_history.xml과의 바인딩
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        intent = getIntent()
        if(intent != null) {
            if(intent.getSerializableExtra("called_list")!=null) {
                hislist = intent.getSerializableExtra("called_list") as ArrayList<History>
                if (hislist.isNotEmpty()) {
                    for (i in hislist) {
                        data.add(0,History(i.name,i.number,i.time))
                    }
                }
            }
        }
        binding.trashHis.setOnClickListener {
            data.clear()
            Toast.makeText(this, "통화기록이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            refreshRecyclerView()// recyclerView 데이터바인딩
        }
        refreshRecyclerView()// recyclerView 데이터바인딩

        //첫번째 화면으로 이동
        binding.btCodRecycle.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    //initialize() test data값 초기화
    private fun initialize(){
        with(data){
            add(History("Member1","Member1","Member1"))
        }
    }
    //activity_history에 있는 recyclerview와 어댑터를 연결함. 어댑터의 배열 listData에 HistoryActivity의 배열 data를 넘김 -> 어댑터를 새로 연결함으로써 어댑터의 data를 새로고침한 셈이다.
    //이렇게 해도 되지만, 리사이클러뷰를 새로고침하기 위해 간단하게 adapter.notifyDataSetChange()만 호출하면 된다.
    //그렇게 하려면 adapter를 클래스의 전역변수로 두고, RecyclerView를 만드는 코드는 onCreate에 만든 다음,
    //새로고침 하려는 부분에서 adapter.notifyDataSetChange를 적으면 된다.
    private fun refreshRecyclerView(){
        val adapter = HistoryAdapter()
        adapter.listData=data
        binding.recyclerviewHistory.adapter=adapter //연결
        binding.recyclerviewHistory.layoutManager = LinearLayoutManager(this) //연결
    }
}