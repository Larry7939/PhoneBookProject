package com.example.thirdproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thirdproject.databinding.ItemRecyclerviewBinding

class HistoryAdapter:RecyclerView.Adapter<Holder>(){
    //History 객체가 들어갈 배열
    //이 배열에 들어갈 내용은 HistoryActivity에서 refreshRecyclerView로 넘겨준다.
    var listData = mutableListOf<History>()

    //어떤 뷰를 만들 것인가
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }
    //생성된 뷰에 어떤 데이터를 넣을 것인가
    //position에 해당하는 데이터를 뷰홀더에 표시
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val member = listData[position]
        holder.setData(member)
    }
    //몇개의 목록을 만들지를 반환
    override fun getItemCount(): Int {
        return listData.size
    }
}
//ViewHolder -> 각 목록 레이아웃에 필요한 기능들을 구현하는 공간
class Holder(val binding:ItemRecyclerviewBinding):RecyclerView.ViewHolder(binding.root){
    fun setData(history: History){
        binding.itemRecyclerviewTv.text = history.name
        binding.itemRecyclerviewFirst.text = history.name.slice(IntRange(0,0))
        binding.itemRecyclerviewTv2.text = history.number
        binding.recyclerviewTime.text = history.time
    }
}

//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        binding = ItemRecyclerviewBinding.inflate(inflater,parent,false)
//        var firstText:String = datalist[position].name
//        binding.itemRecyclerviewFirst.text = firstText.slice(IntRange(0,0))
//        binding.itemRecyclerviewTv.text = datalist[position].name
//        binding.itemRecyclerviewTv2.text = datalist[position].number
//        return binding.root
