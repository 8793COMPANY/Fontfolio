package com.corporation8793.fontfolio.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import com.corporation8793.fontfolio.R
import com.corporation8793.fontfolio.common.Fontfolio
import com.corporation8793.fontfolio.login.AppLoginActivity
import com.corporation8793.fontfolio.login.LoginActivity

class Join : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        val fontfolio = Fontfolio().getInstance(applicationContext)
        val sign_up_btn : LinearLayout = findViewById(R.id.sign_up_btn)
        val log_in_btn : LinearLayout = findViewById(R.id.log_in_btn)

        fontfolio.xlsToRoom()

        sign_up_btn.setOnClickListener {
            fontfolio.moveToActivity(this, SignUp::class.java, false)
        }

        log_in_btn.setOnClickListener {
            fontfolio.moveToActivity(this, LoginActivity::class.java, false)
        }
    }
}