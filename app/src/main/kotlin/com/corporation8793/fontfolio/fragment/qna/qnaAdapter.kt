package com.corporation8793.fontfolio.fragment.qna

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.corporation8793.fontfolio.R
import com.corporation8793.fontfolio.activity.FontInformation
import com.corporation8793.fontfolio.activity.QuestionInformation
import com.corporation8793.fontfolio.library.room.entity.font.Font
import com.corporation8793.fontfolio.library.room.entity.qna.Question
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.absoluteValue

class qnaAdapter(val context : Context, val activity : FragmentActivity) : RecyclerView.Adapter<qnaAdapter.ItemViewHolder>(){
    var datas = mutableListOf<Question>()

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.image)
        val questionTitle = itemView.findViewById<TextView>(R.id.questionTitle)
        val questionViews = itemView.findViewById<TextView>(R.id.questionViews)
        val questionDate = itemView.findViewById<TextView>(R.id.questionDate)

        fun bind(question : Question) {
            questionTitle.text = question.questionTitle
            questionViews.text = "${question.questionViews} views"
            // 이미지는 웹에서
            Glide.with(context).load("${question.questionPhotoPath}")
                .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    image.background = resource
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })

            // 캘린더 기반으로 추산.
            val cal = Calendar.getInstance()
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val ymd = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

            val current_time = LocalDateTime.parse(sdf.format(cal.time).toString(), dtf)
            val saved_time = LocalDateTime.parse(question.questionDate, dtf)
            val days = Duration.between(current_time, saved_time).toDays().toInt().absoluteValue

            when {
                (days == 0) -> questionDate.text = "today"
                (days <= 31) -> questionDate.text = "$days days ago"
                else -> questionDate.text = saved_time.format(ymd)
            }
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
            var intent = Intent(activity, QuestionInformation::class.java)
            intent.putExtra("qid", datas[position].qid)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

        val layoutParams = StaggeredGridLayoutManager.LayoutParams(holder.itemView.layoutParams)

        layoutParams.apply {
            isFullSpan = false
            holder.itemView.layoutParams = this
        }
    }

    override fun getItemCount(): Int = datas.size
}