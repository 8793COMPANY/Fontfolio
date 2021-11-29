package com.corporation8793.fontfolio.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.corporation8793.fontfolio.R
import com.corporation8793.fontfolio.library.room.entity.Font

class FontInformation(font : Font) : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_font_information)
    }
}