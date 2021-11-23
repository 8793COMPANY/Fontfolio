package com.corporation8793.fontfolio.activity

import android.content.res.ColorStateList
import android.graphics.Rect
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod
import android.view.View
import android.view.inputmethod.EditorInfo
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
    lateinit var welcome_text_1 : TextView
    lateinit var welcome_text_2 : TextView
    lateinit var input_email_div : ConstraintLayout
    lateinit var input_email : EditText
    lateinit var input_email_init : AppCompatButton
    lateinit var sign_up_error : ImageView
    lateinit var back_btn : Button
    lateinit var next_btn : LinearLayout

    // step2 - widget
    lateinit var view_pw_btn : LinearLayout
    lateinit var view_pw_chk : ImageView
    var view_pw : Boolean = false

    // step3 - widget
    lateinit var input_name_div : ConstraintLayout
    lateinit var input_name : EditText
    lateinit var input_name_init : AppCompatButton
    lateinit var input_name_edit : AppCompatButton
    lateinit var sign_up_name_error : ImageView
    var edit_name_state : Boolean = false

    // step1 & total function
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // step1 - init
        progressBar = findViewById(R.id.progressBar)
        main_div = findViewById(R.id.main_div)
        progressText = findViewById(R.id.progressText)
        step1 = findViewById(R.id.step1)
        welcome_text_1 = findViewById(R.id.welcome_text_1)
        welcome_text_2 = findViewById(R.id.welcome_text_2)
        input_email_div = findViewById(R.id.input_email_div)
        input_email = findViewById(R.id.input_email)
        input_email_init = findViewById(R.id.input_email_init)
        sign_up_error = findViewById(R.id.sign_up_error)
        back_btn = findViewById(R.id.back_btn)
        next_btn = findViewById(R.id.next_btn)
        next_btn.isEnabled = false

        back_btn.setOnClickListener { finish() }

        input_email_init.setOnClickListener {
            input_email.text.clear()
        }

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
                        input_email_init.visibility = View.VISIBLE
                        isValidEmail(input_email)
                    } else {
                        next_btn.backgroundTintList = (ColorStateList.valueOf(resources.getColor(R.color.btn_gray, theme)))
                        next_btn.isEnabled = false
                        input_email_init.visibility = View.GONE
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

        // TODO : For test
        step4()
    }

    fun step2() {
        // step2 - init
        view_pw_btn = findViewById(R.id.view_pw_btn)
        view_pw_chk = findViewById(R.id.view_pw_chk)
        view_pw_btn.visibility = View.VISIBLE

        welcome_text_1.text = "Carefully :)"
        welcome_text_2.text = "Create your own password."

        input_email.hint = "Enter more than 6-digits"
        input_email.inputType = EditorInfo.TYPE_TEXT_VARIATION_PASSWORD
        input_email.transformationMethod = PasswordTransformationMethod()

            input_email.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    if (s != null) {
                        if (s.isEmpty()) {
                            next_btn.backgroundTintList = (ColorStateList.valueOf(resources.getColor(R.color.btn_gray, theme)))
                            next_btn.isEnabled = false
                            sign_up_error.background = resources.getDrawable(R.drawable.sign_up_error1, theme)
                            sign_up_error.visibility = View.VISIBLE
                            input_email_init.visibility = View.GONE
                        } else if (s.length < 6) {
                            next_btn.backgroundTintList = (ColorStateList.valueOf(resources.getColor(R.color.btn_gray, theme)))
                            next_btn.isEnabled = false
                            sign_up_error.background = resources.getDrawable(R.drawable.sign_up_error5, theme)
                            sign_up_error.visibility = View.VISIBLE
                        } else if (s.length > 14) {
                            next_btn.backgroundTintList = (ColorStateList.valueOf(resources.getColor(R.color.btn_gray, theme)))
                            next_btn.isEnabled = false
                            sign_up_error.background = resources.getDrawable(R.drawable.sign_up_error3, theme)
                            sign_up_error.visibility = View.VISIBLE
                        } else {
                            next_btn.backgroundTintList = (ColorStateList.valueOf(resources.getColor(R.color.btn_red, theme)))
                            next_btn.isEnabled = true
                            input_email_init.visibility = View.VISIBLE
                            sign_up_error.visibility = View.GONE
                        }
                    }
                }
            }
        )

        next_btn.backgroundTintList = (ColorStateList.valueOf(resources.getColor(R.color.btn_gray, theme)))
        next_btn.isEnabled = false
        input_email.text.clear()

        step = 2
        progressText.text = "Step $step of 3"
        progressBar.progress = 2
        main_div.setHorizontalBias(R.id.progressText, 0.45f)

        view_pw_btn.setOnClickListener {
            view_pw = !view_pw
            if(view_pw) {
                view_pw_chk.background = resources.getDrawable(R.drawable.view_pw_on_btn, theme)
                input_email.transformationMethod = HideReturnsTransformationMethod()
            } else {
                view_pw_chk.background = resources.getDrawable(R.drawable.view_pw_off_btn, theme)
                input_email.transformationMethod = PasswordTransformationMethod()
            }
        }

        next_btn.setOnClickListener {
            step3()
        }
    }

    fun step3() {
        // step3 - init
        input_name_div = findViewById(R.id.input_name_div)
        input_name = findViewById(R.id.input_name)
        input_name_init = findViewById(R.id.input_name_init)
        input_name_edit = findViewById(R.id.input_name_edit)
        sign_up_name_error = findViewById(R.id.sign_up_name_error)

        input_name_div.visibility = View.VISIBLE
        view_pw_btn.visibility = View.GONE

        input_name_init.setOnClickListener {
            input_name.text.clear()
        }

        input_name_edit.setOnClickListener {
            edit_name_state = !edit_name_state
            if(edit_name_state) {
                input_name.isEnabled = true
                input_name.maxLines = 1
                input_name.requestFocus()

                input_name_edit.background = resources.getDrawable(R.drawable.edit_name_done, theme)
            } else {
                input_name.isEnabled = false
                input_name_init.visibility = View.GONE

                input_name_edit.background = resources.getDrawable(R.drawable.edit_name_img, theme)
            }
        }

        input_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if (s.isEmpty()) {
                        next_btn.backgroundTintList = (ColorStateList.valueOf(resources.getColor(R.color.btn_gray, theme)))
                        next_btn.isEnabled = false
                        input_name_init.visibility = View.GONE
                        sign_up_name_error.visibility = View.VISIBLE
                    } else {
                        next_btn.backgroundTintList = (ColorStateList.valueOf(resources.getColor(R.color.btn_red, theme)))
                        next_btn.isEnabled = true
                        input_name_init.visibility = View.VISIBLE
                        sign_up_name_error.visibility = View.INVISIBLE
                    }
                }
            }

        })

        welcome_text_1.text = "Hello :)"
        welcome_text_2.text = "Let me know your age."

        input_email.hint = "Your age"
        input_email.inputType = EditorInfo.TYPE_CLASS_NUMBER
        input_email.transformationMethod = HideReturnsTransformationMethod()

        input_email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if (s.isEmpty()) {
                        next_btn.backgroundTintList = (ColorStateList.valueOf(resources.getColor(R.color.btn_gray, theme)))
                        next_btn.isEnabled = false
                        sign_up_error.background = resources.getDrawable(R.drawable.sign_up_error6, theme)
                        sign_up_error.visibility = View.VISIBLE
                        input_email_init.visibility = View.GONE
                    } else if (s.toString().toInt() > 99) {
                        next_btn.backgroundTintList = (ColorStateList.valueOf(resources.getColor(R.color.btn_gray, theme)))
                        next_btn.isEnabled = false
                        sign_up_error.background = resources.getDrawable(R.drawable.sign_up_error4, theme)
                        sign_up_error.visibility = View.VISIBLE
                    } else {
                        next_btn.backgroundTintList = (ColorStateList.valueOf(resources.getColor(R.color.btn_red, theme)))
                        next_btn.isEnabled = true
                        input_email_init.visibility = View.VISIBLE
                        sign_up_error.visibility = View.GONE
                    }
                }
            }
        }
        )

        next_btn.backgroundTintList = (ColorStateList.valueOf(resources.getColor(R.color.btn_gray, theme)))
        next_btn.isEnabled = false
        input_email.text.clear()

        step = 3
        progressText.text = "Step $step of 3"
        progressBar.progress = 3
        main_div.setHorizontalBias(R.id.progressText, 0.9f)

        next_btn.setOnClickListener {
            if (input_name.text.isEmpty()) {
                sign_up_name_error.visibility = View.VISIBLE
                next_btn.backgroundTintList = (ColorStateList.valueOf(resources.getColor(R.color.btn_gray, theme)))
                next_btn.isEnabled = false
            } else if (input_email.text.toString().toInt() > 99) {
                sign_up_error.background = resources.getDrawable(R.drawable.sign_up_error4, theme)
                sign_up_error.visibility = View.VISIBLE
                input_email.requestFocus()
                next_btn.backgroundTintList = (ColorStateList.valueOf(resources.getColor(R.color.btn_gray, theme)))
                next_btn.isEnabled = false
            } else if (input_email.text.isEmpty()) {
                sign_up_error.background = resources.getDrawable(R.drawable.sign_up_error6, theme)
                sign_up_error.visibility = View.VISIBLE
                input_email_init.visibility = View.GONE
                input_email.requestFocus()
                next_btn.backgroundTintList = (ColorStateList.valueOf(resources.getColor(R.color.btn_gray, theme)))
                next_btn.isEnabled = false
            } else {
                step4()
            }
        }
    }

    fun step4() {
        // step3 - init
        welcome_text_1.text = "Finally :)"
        welcome_text_2.text = "Choose your occupation group,\nYou can find more related content."

        input_name_div.visibility = View.GONE
        input_email_div.visibility = View.GONE


        step = 3
        progressText.text = "FontFolio"
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