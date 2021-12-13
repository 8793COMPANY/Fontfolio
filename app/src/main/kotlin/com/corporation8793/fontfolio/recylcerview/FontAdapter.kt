package com.corporation8793.fontfolio.recylcerview

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.corporation8793.fontfolio.R
import com.corporation8793.fontfolio.activity.FontInformation
import com.corporation8793.fontfolio.common.Fontfolio

import com.corporation8793.fontfolio.library.room.entity.font.Font


class FontAdapter(val context: Context, val activity:FragmentActivity?) : RecyclerView.Adapter<FontAdapter.ItemViewHolder>(){
    var datas = mutableListOf<Font>()
    var colors = arrayOf("#eaebde", "#ebc4ff", "#08000000", "#f8eddf","#F8EDDF","#EDF1FF","#F8DEDE","#DEEBE8","#0D000000")

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

                val fontName = itemView.findViewById<TextView>(R.id.font_name)
                val copyright = itemView.findViewById<TextView>(R.id.font_copyright)
                val content = itemView.findViewById<TextView>(R.id.content)

                fun bind(font: Font){
                    Log.e("copyright", font.fontName)
                    fontName.text = font.fontName
                    copyright.text = font.fontCopyrightHolder
                    content.text = "Almost before we knew it, we had left the ground."
                    Log.e("fontname",font.fontName.toLowerCase().replace(" ", "_").replace("-","_")+".ttf")
                    Fontfolio().changeFontOfTextView(activity,content,font.fontName)
                }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.font_list_itemview,
            parent,
            false
        )
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(datas[position])

        Log.e("in", "check")

        holder.itemView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(colors[(0..8).random()])))

        holder.itemView.setOnClickListener({
            Log.e("item", "click!")
            var intent :Intent = Intent(activity,FontInformation::class.java)
            intent.putExtra("fontName",datas[position].fontName)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)

        })

        val layoutParams = StaggeredGridLayoutManager.LayoutParams(
            holder.itemView.getLayoutParams())

        if (holder.fontName.text.length > 30 || holder.content.text.length > 52){
            layoutParams.isFullSpan = true
        } else{
            layoutParams.isFullSpan = false
        }

        holder.itemView.layoutParams = layoutParams
    }

    override fun getItemCount(): Int = datas.size
}