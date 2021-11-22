package com.corporation8793.fontfolio.activity

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.annotation.IdRes
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.corporation8793.fontfolio.R
import com.google.android.material.progressindicator.LinearProgressIndicator

class SignUp : AppCompatActivity() {
    // step1 - widget
    lateinit var progressBar : LinearProgressIndicator
    lateinit var main_div : ConstraintLayout
    lateinit var progressText : TextView
    var step = 1
    lateinit var step1 : ConstraintLayout
    lateinit var input_email : EditText
    lateinit var input_email_init : AppCompatButton
    lateinit var sign_up_error : ImageView
    lateinit var back_btn : Button
    lateinit var next_btn : LinearLayout

    // step2 - widget

    // step3 - widget

    // step1 & total function
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // step1 - init
        progressBar = findViewById(R.id.progressBar)
        main_div = findViewById(R.id.main_div)
        progressText = findViewById(R.id.progressText)
        step1 = findViewById(R.id.step1)
        input_email = findViewById(R.id.input_email)
        input_email_init = findViewById(R.id.input_email_init)
        sign_up_error = findViewById(R.id.sign_up_error)
        back_btn = findViewById(R.id.back_btn)
        next_btn = findViewById(R.id.next_btn)
        next_btn.isEnabled = false

        back_btn.setOnClickListener { finish() }

        next_btn.setOnClickListener {
            step2()
        }

        input_email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if (s.isNotEmpty()) {
                        isValidEmail(input_email)
                    } else {
                        next_btn.backgroundTintList = (ColorStateList.valueOf(resources.getColor(R.color.btn_gray, theme)))
                        next_btn.isEnabled = false
                        sign_up_error.visibility = View.INVISIBLE
                    }
                }
            }

            fun isValidEmail(edt : EditText) {
                if (!isEmailValid(edt.text.toString())) {
                    next_btn.backgroundTintList = (ColorStateList.valueOf(resources.getColor(R.color.btn_gray, theme)))
                    next_btn.isEnabled = false
                    sign_up_error.visibility = View.VISIBLE
                } else {
                    next_btn.backgroundTintList = (ColorStateList.valueOf(resources.getColor(R.color.btn_red, theme)))
                    next_btn.isEnabled = true
                    sign_up_error.visibility = View.INVISIBLE
                }
            }

            fun isEmailValid(email : CharSequence) = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        })
    }

    fun step2() {
        // step2 - init
        step1.visibility = View.GONE
        next_btn.backgroundTintList = (ColorStateList.valueOf(resources.getColor(R.color.btn_gray, theme)))
        next_btn.isEnabled = false

        step = 2
        progressText.text = "Step $step of 3"
        progressBar.progress = 2
        main_div.setHorizontalBias(R.id.progressText, 0.45f)
    }

    fun step3() {
        // step3 - init
        step = 3
        progressText.text = "Step $step of 3"
        progressBar.progress = 3
        main_div.setHorizontalBias(R.id.progressText, 0.9f)
    }

    fun ConstraintLayout.setHorizontalBias(@IdRes targetViewId: Int, bias: Float) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.setHorizontalBias(targetViewId, bias)
        constraintSet.applyTo(this)
    }
}