package com.corporation8793.fontfolio.fragment.qna

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.corporation8793.fontfolio.R
import com.corporation8793.fontfolio.activity.FontInformation
import com.corporation8793.fontfolio.library.room.entity.font.Font
import com.corporation8793.fontfolio.library.room.entity.qna.Question

class qnaAdapter(val context : Context, val activity : FragmentActivity) : RecyclerView.Adapter<qnaAdapter.ItemViewHolder>(){
    var datas = mutableListOf<Question>()

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.image)
        val questionTitle = itemView.findViewById<TextView>(R.id.questionTitle)
        val questionViews = itemView.findViewById<TextView>(R.id.questionViews)
        val questionDate = itemView.findViewById<TextView>(R.id.questionDate)

        fun bind(question : Question){
            image.background = context.resources.getDrawable(R.drawable.test_background_5, activity.theme)
            questionTitle.text = question.questionTitle
            questionViews.text = "${question.questionViews} views"
            // TODO : 캘린더 기반으로 추산.
            questionDate.text = "${question.questionDate.time}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.qna_list_itemview,
            parent,
            false
        )
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(datas[position])

        holder.itemView.setOnClickListener {
            /*
            var intent: Intent = Intent(activity, FontInformation::class.java)
            intent.putExtra("fontName", datas[position].qid)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
             */
            Toast.makeText(activity, "click", Toast.LENGTH_SHORT).show()
        }

        val layoutParams = StaggeredGridLayoutManager.LayoutParams(holder.itemView.layoutParams)

        layoutParams.apply {
            isFullSpan = false
            holder.itemView.layoutParams = this
        }
    }

    override fun getItemCount(): Int = datas.size
}