package com.corporation8793.fontfolio.fragment.search

import android.content.Intent
import android.text.SpannableString
import android.text.SpannableStringBuilder
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
import com.corporation8793.fontfolio.library.room.entity.font.Font


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
            // TODO : 대소문자 구분 없이 볼드처리
            if (dataSet[position].fontName.contains(boldText.toString(), false)) {
                append("${dataSet[position].fontName.substringBefore(boldText.toString(), "")}")
                bold { append(boldText.toString()) }
                append("${dataSet[position].fontName.substringAfter(boldText.toString(), "")}")
            } else {
                val list = dataSet[position].fontName.indexesOf(boldText.toString(), true)
                for ((i, c) in dataSet[position].fontName.withIndex()) {
                    if (list[0] > i) {
                        append(c)
                    }
                }
                val endIndex = (list[0]+(boldText.toString().length - 1))
                bold { append(dataSet[position].fontName.substring(list[0]..endIndex)) }
                append(dataSet[position].fontName.substring(endIndex + 1))
            }
        }

        listDataSet.add(listData(position = position, font_name = dataSet[position].fontName))

        holder.font_name_div.setOnClickListener {
            Log.e("onBindViewHolder", "onBindViewHolder: ${dataSet[position].fontName}")
            mFragment.search_bar_input.text.clear()
            mFragment.activity.apply {
                startActivity(Intent(this, FontInformation().javaClass)
                    .putExtra("fontName", dataSet[position].fontName))
            }
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun String?.indexesOf(substr: String, ignoreCase: Boolean = true): List<Int> {
        tailrec fun String.collectIndexesOf(offset: Int = 0, indexes: List<Int> = emptyList()): List<Int> =
            when (val index = indexOf(substr, offset, ignoreCase)) {
                -1 -> indexes
                else -> collectIndexesOf(index + substr.length, indexes + index)
            }

        return when (this) {
            null -> emptyList()
            else -> collectIndexesOf()
        }
    }

    data class listData(
        var position : Int,
        var font_name : String
    )
}