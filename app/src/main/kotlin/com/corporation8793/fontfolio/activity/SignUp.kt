package com.corporation8793.fontfolio.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.corporation8793.fontfolio.R
import com.google.android.material.progressindicator.LinearProgressIndicator

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        var step = 1
        val progressBar : LinearProgressIndicator = findViewById(R.id.progressBar)
        val main_div : ConstraintLayout = findViewById(R.id.main_div)
        val progressText : TextView = findViewById(R.id.progressText)

        /*
        step = 2
        progressText.text = "Step $step of 3"
        progressBar.progress = 2
        main_div.setHorizontalBias(R.id.progressText, 0.45f)

        step = 3
        progressText.text = "Step $step of 3"
        progressBar.progress = 3
        main_div.setHorizontalBias(R.id.progressText, 0.9f)
         */
    }

    fun ConstraintLayout.setHorizontalBias(@IdRes targetViewId: Int, bias: Float) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.setHorizontalBias(targetViewId, bias)
        constraintSet.applyTo(this)
    }
}