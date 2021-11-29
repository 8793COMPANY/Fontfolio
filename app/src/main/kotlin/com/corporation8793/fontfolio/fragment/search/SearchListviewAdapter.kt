package com.corporation8793.fontfolio.fragment.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.fontfolio.R
import com.corporation8793.fontfolio.library.room.entity.Font


class SearchListviewAdapter(
    val mFragment: SearchFragment,
    private val dataSet: List<Font>,
    val listDataSet: MutableList<listData> = mutableListOf()
) : RecyclerView.Adapter<SearchListviewAdapter.Holder>() {
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fontName : TextView = itemView.findViewById(R.id.font_name)
        var font_name_div : ConstraintLayout = itemView.findViewById(R.id.font_name_div)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_listview_item, parent, false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setIsRecyclable(false)
        holder.fontName.text = dataSet[position].fontName
        listDataSet.add(listData(position = position, font_name = dataSet[position].fontName))

        holder.font_name_div.setOnClickListener {
            Toast.makeText(mFragment.activity, "${dataSet[position].fontName} 페이지로 이동", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    data class listData(
        var position : Int,
        var font_name : String
    )
}