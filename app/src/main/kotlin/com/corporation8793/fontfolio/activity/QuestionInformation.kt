package com.corporation8793.fontfolio.activity

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.corporation8793.fontfolio.R
import com.corporation8793.fontfolio.common.Fontfolio
import com.corporation8793.fontfolio.library.room.entity.qna.Answer
import com.corporation8793.fontfolio.library.room.entity.qna.Question
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.absoluteValue

class QuestionInformation : AppCompatActivity() {
    lateinit var back_btn : AppCompatButton
    lateinit var image : ImageView
    lateinit var questionTitle : TextView
    lateinit var questioner : TextView
    lateinit var questionViews : TextView
    lateinit var questionDate : TextView
    lateinit var latest_comment_div : ConstraintLayout
    lateinit var latest_comment_profile_image : ImageView
    lateinit var latest_comment : TextView
    lateinit var heart_div : LinearLayout
    lateinit var heart_icon : ImageView
    lateinit var heart_count : TextView
    lateinit var reply_div : LinearLayout
    lateinit var helpful_div : LinearLayout
    lateinit var helpful_icon : ImageView
    lateinit var helpful_text : TextView
    lateinit var helpful_count : TextView
    lateinit var add_comment_div : ConstraintLayout

    lateinit var fontfolio : Fontfolio
    lateinit var question : Question
    var q_index : Int = 0
    lateinit var a_list : MutableList<Answer>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_information)

        back_btn = findViewById(R.id.back_btn)
        image = findViewById(R.id.image)
        questionTitle = findViewById(R.id.questionTitle)
        questioner = findViewById(R.id.questioner)
        questionViews = findViewById(R.id.questionViews)
        questionDate = findViewById(R.id.questionDate)
        latest_comment_div = findViewById(R.id.latest_comment_div)
        latest_comment_profile_image = findViewById(R.id.latest_comment_profile_image)
        latest_comment = findViewById(R.id.latest_comment)
        heart_div = findViewById(R.id.heart_div)
        heart_icon = findViewById(R.id.heart_icon)
        heart_count = findViewById(R.id.heart_count)
        reply_div = findViewById(R.id.reply_div)
        helpful_div = findViewById(R.id.helpful_div)
        helpful_icon = findViewById(R.id.helpful_icon)
        helpful_text = findViewById(R.id.helpful_text)
        helpful_count = findViewById(R.id.helpful_count)
        add_comment_div = findViewById(R.id.add_comment_div)

        fontfolio = Fontfolio().getInstance(applicationContext)
        q_index = intent.getIntExtra("qid", 0)

        // 데이터 로딩
        CoroutineScope(Dispatchers.IO).launch {
            fun getQuestion() : Question {
                return fontfolio.db.questionDao().findByQid(q_index)
            }

            val q_list_load = async {
                getQuestion()
            }
            question = q_list_load.await()
            Log.e("QuestionInformation", "$question")

            // 뷰 초기화
            CoroutineScope(Dispatchers.Main).launch {
                questionTitle.text = question.questionTitle
                questionViews.text = "${question.questionViews} views"
                // 이미지는 웹에서
                Glide.with(applicationContext).load("${question.questionPhotoPath}")
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

        back_btn.setOnClickListener { finish() }
        heart_div.setOnClickListener {
            // TODO :
            heart_icon.setImageResource(R.drawable.heart_on)
        }
    }
}