package com.corporation8793.fontfolio.recylcerview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.fontfolio.R

class FontAdapter(val context: Context) : RecyclerView.Adapter<FontAdapter.ItemViewHolder>(){
    var datas = mutableListOf<FontItem>()

    inner class ItemViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {

                val fontName = itemView.findViewById<TextView>(R.id.font_name)
                val copyright = itemView.findViewById<TextView>(R.id.font_copyright)
                val content = itemView.findViewById<TextView>(R.id.content)

                fun bind(font: FontItem, context: Context){
                    fontName.text = font.fontName
                    copyright.text = font.copyright
                    content.text = "Almost before we knew it, we had left the ground."
                }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.font_list_itemview, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(datas[position],context)

        holder.itemView.setOnClickListener({
            Log.e("item","click!")
        })
    }

    override fun getItemCount(): Int = datas.size
}