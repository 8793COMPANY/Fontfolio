package com.corporation8793.fontfolio.fragment.search

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.fontfolio.R
import com.corporation8793.fontfolio.activity.FontInformation
import com.corporation8793.fontfolio.common.Fontfolio
import com.corporation8793.fontfolio.library.room.entity.Font


class SearchListviewAdapter(
    val mFragment: SearchFragment,
    private val dataSet: List<Font>,
    val listDataSet: MutableList<listData> = mutableListOf(),
    val boldText: CharSequence? = null
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
        holder.fontName.text = buildSpannedString {
            append("${dataSet[position].fontName.substringBefore(boldText.toString())}")
            bold { append(boldText.toString()) }
            append("${dataSet[position].fontName.substringAfter(boldText.toString())}")
        }

        listDataSet.add(listData(position = position, font_name = dataSet[position].fontName))

        holder.font_name_div.setOnClickListener {
            Log.e("onBindViewHolder", "onBindViewHolder: ${dataSet[position].fontName}")
            mFragment.activity.apply {
                startActivity(Intent(this, FontInformation().javaClass)
                    .putExtra("fontName", dataSet[position].fontName))
            }
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