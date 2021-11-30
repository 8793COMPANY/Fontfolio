package com.corporation8793.fontfolio.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.corporation8793.fontfolio.R
import com.corporation8793.fontfolio.library.room.entity.Font

class FontInformation(val font : Font) : AppCompatActivity() {
    lateinit var search_bar_input : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_font_information)

        search_bar_input = findViewById(R.id.search_bar_input)
        search_bar_input.setText(font.fontName)
    }
}