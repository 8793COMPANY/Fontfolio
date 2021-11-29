package com.corporation8793.fontfolio.recylcerview

import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.recyclerview.widget.RecyclerView

class FontAdapter() : RecyclerView.Adapter<FontAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(itemView:View):
            RecyclerView.ViewHolder(itemView){

                fun bind(){

                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind()

        holder.itemView
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}