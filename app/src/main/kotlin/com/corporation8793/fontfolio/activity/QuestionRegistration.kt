package com.corporation8793.fontfolio.activity

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.corporation8793.fontfolio.R
import java.io.InputStream

class QuestionRegistration : AppCompatActivity() {
    lateinit var image : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_registration)

        image = findViewById(R.id.image)

        intent.data.let {
            val inputStream : InputStream? = this.contentResolver.openInputStream(it!!)
            val drawable = Drawable.createFromStream(inputStream, it.toString())
            image.background = drawable
        }
    }
}