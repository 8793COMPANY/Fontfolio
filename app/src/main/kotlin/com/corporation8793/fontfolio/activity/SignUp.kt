package com.corporation8793.fontfolio.activity

import android.content.Context
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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.IdRes
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.corporation8793.fontfolio.LoadingActivity
import com.corporation8793.fontfolio.R
import com.corporation8793.fontfolio.common.Fontfolio
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
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

    // step4 - widget
    lateinit var actionBar : ConstraintLayout
    lateinit var welcome_text_3 : TextView
    lateinit var group_select_div : ConstraintLayout
    lateinit var group_select_text : TextView
    lateinit var group_select_error : ImageView
    lateinit var group_type_more_div : ConstraintLayout
    lateinit var group_type_more_input : EditText
    lateinit var group_type_more_input_init : ImageView
    lateinit var group_type_more_error : ImageView

    lateinit var job_architecture : LinearLayout
    lateinit var job_art : LinearLayout
    lateinit var job_broadcast : LinearLayout
    lateinit var job_business : LinearLayout
    lateinit var job_chemistry : LinearLayout
    lateinit var job_education : LinearLayout
    lateinit var job_electronic : LinearLayout
    lateinit var job_food : LinearLayout
    lateinit var job_health : LinearLayout
    lateinit var job_it : LinearLayout
    lateinit var job_sports : LinearLayout
    lateinit var job_etc : LinearLayout

    lateinit var bottom_sheet_close_btn : ImageView

    lateinit var bottomSheetView : View
    lateinit var bottomSheetDialog : BottomSheetDialog

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
//        step2()
//        step3()
//        step4()
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
        // step4 - init
        actionBar = findViewById(R.id.actionBar)
        welcome_text_3 = findViewById(R.id.welcome_text_3)
        group_select_div = findViewById(R.id.group_select_div)
        group_select_text = findViewById(R.id.group_select_text)
        group_select_error = findViewById(R.id.group_select_error)
        group_type_more_div = findViewById(R.id.group_type_more_div)
        group_type_more_input = findViewById(R.id.group_type_more_input)
        group_type_more_input_init = findViewById(R.id.group_type_more_input_init)
        group_type_more_error = findViewById(R.id.group_type_more_error)

        bottomSheetView = layoutInflater.inflate(R.layout.group_select_sheet_layout, null)
        bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetDialog.behavior.skipCollapsed = true
        bottomSheetDialog.behavior.saveFlags = BottomSheetBehavior.SAVE_SKIP_COLLAPSED
        bottomSheetDialog.behavior.isFitToContents = true
        bottomSheetDialog.setContentView(bottomSheetView)

        job_architecture = bottomSheetView.findViewById(R.id.job_architecture)
        job_art = bottomSheetView.findViewById(R.id.job_art)
        job_broadcast = bottomSheetView.findViewById(R.id.job_broadcast)
        job_business = bottomSheetView.findViewById(R.id.job_business)
        job_chemistry = bottomSheetView.findViewById(R.id.job_chemistry)
        job_education = bottomSheetView.findViewById(R.id.job_education)
        job_electronic = bottomSheetView.findViewById(R.id.job_electronic)
        job_food = bottomSheetView.findViewById(R.id.job_food)
        job_health = bottomSheetView.findViewById(R.id.job_health)
        job_it = bottomSheetView.findViewById(R.id.job_it)
        job_sports = bottomSheetView.findViewById(R.id.job_sports)
        job_etc = bottomSheetView.findViewById(R.id.job_etc)
        bottom_sheet_close_btn = bottomSheetView.findViewById(R.id.bottom_sheet_close_btn)

        welcome_text_1.text = "Finally :)"
        welcome_text_2.text = "Choose your occupation group,\nYou can find more related content."
        next_btn.backgroundTintList = (ColorStateList.valueOf(resources.getColor(R.color.btn_gray, theme)))
        next_btn.isEnabled = false
        input_email.text.clear()

        actionBar.visibility = View.INVISIBLE
        input_name_div.visibility = View.GONE
        input_email_div.visibility = View.GONE

        group_select_div.visibility = View.VISIBLE

        group_select_div.setOnClickListener {
            bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetDialog.behavior.skipCollapsed = true
            bottomSheetDialog.behavior.saveFlags = BottomSheetBehavior.SAVE_SKIP_COLLAPSED
            bottomSheetDialog.behavior.isFitToContents = true

            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }

        next_btn.setOnClickListener {
            if (group_select_text.text.isEmpty()) {
                group_select_error.visibility = View.VISIBLE
                next_btn.backgroundTintList = (ColorStateList.valueOf(resources.getColor(R.color.btn_gray, theme)))
                next_btn.isEnabled = false
            } else if (group_type_more_input.text.isEmpty()) {
                group_type_more_error.visibility = View.VISIBLE
                group_type_more_input_init.visibility = View.INVISIBLE
                group_type_more_input.requestFocus()
                next_btn.backgroundTintList = (ColorStateList.valueOf(resources.getColor(R.color.btn_gray, theme)))
                next_btn.isEnabled = false
            } else {
                Fontfolio().moveToActivity(this, LoadingActivity::class.java, true)
            }
        }

        group_type_more_input.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (group_type_more_input.text.isEmpty()) {
                    group_type_more_error.visibility = View.VISIBLE
                    group_type_more_input_init.visibility = View.INVISIBLE
                    next_btn.backgroundTintList = (ColorStateList.valueOf(resources.getColor(R.color.btn_gray, theme)))
                    next_btn.isEnabled = false
                } else {
                    group_type_more_error.visibility = View.INVISIBLE
                    group_type_more_input_init.visibility = View.VISIBLE
                    next_btn.backgroundTintList = (ColorStateList.valueOf(resources.getColor(R.color.btn_red, theme)))
                    next_btn.isEnabled = true
                }
            }
        })

        group_type_more_input_init.setOnClickListener {
            group_type_more_input.text.clear()
        }

        step = 3
        progressText.text = "FontFolio"
        progressBar.progress = 3
        main_div.setHorizontalBias(R.id.progressText, 0.9f)

        job_architecture.setOnClickListener {
            group_select_text.text = "Architecture / Facilities"
            group_type_more_div.visibility = View.VISIBLE
            group_select_error.visibility = View.INVISIBLE
            bottomSheetDialog.dismiss()
        }
        job_art.setOnClickListener {
            group_select_text.text = "Art / Design / Creation"
            group_type_more_div.visibility = View.VISIBLE
            group_select_error.visibility = View.INVISIBLE
            bottomSheetDialog.dismiss()
        }
        job_broadcast.setOnClickListener {
            group_select_text.text = "Broadcast / Culture"
            group_type_more_div.visibility = View.VISIBLE
            group_select_error.visibility = View.INVISIBLE
            bottomSheetDialog.dismiss()
        }
        job_business.setOnClickListener {
            group_select_text.text = "Business / Management"
            group_type_more_div.visibility = View.VISIBLE
            group_select_error.visibility = View.INVISIBLE
            bottomSheetDialog.dismiss()
        }
        job_chemistry.setOnClickListener {
            group_select_text.text = "Chemistry / Materials"
            group_type_more_div.visibility = View.VISIBLE
            group_select_error.visibility = View.INVISIBLE
            bottomSheetDialog.dismiss()
        }
        job_education.setOnClickListener {
            group_select_text.text = "Education / Research"
            group_type_more_div.visibility = View.VISIBLE
            group_select_error.visibility = View.INVISIBLE
            bottomSheetDialog.dismiss()
        }
        job_electronic.setOnClickListener {
            group_select_text.text = "Electronic / Communication"
            group_type_more_div.visibility = View.VISIBLE
            group_select_error.visibility = View.INVISIBLE
            bottomSheetDialog.dismiss()
        }
        job_food.setOnClickListener {
            group_select_text.text = "Food / Restaurant"
            group_type_more_div.visibility = View.VISIBLE
            group_select_error.visibility = View.INVISIBLE
            bottomSheetDialog.dismiss()
        }
        job_health.setOnClickListener {
            group_select_text.text = "Health / Medical"
            group_type_more_div.visibility = View.VISIBLE
            group_select_error.visibility = View.INVISIBLE
            bottomSheetDialog.dismiss()
        }
        job_it.setOnClickListener {
            group_select_text.text = "IT / Software / Solution"
            group_type_more_div.visibility = View.VISIBLE
            group_select_error.visibility = View.INVISIBLE
            bottomSheetDialog.dismiss()
        }
        job_sports.setOnClickListener {
            group_select_text.text = "Sports / Entertainment"
            group_type_more_div.visibility = View.VISIBLE
            group_select_error.visibility = View.INVISIBLE
            bottomSheetDialog.dismiss()
        }
        job_etc.setOnClickListener {
            group_select_text.text = "Etc & Direct input"
            group_type_more_div.visibility = View.VISIBLE
            group_select_error.visibility = View.INVISIBLE
            bottomSheetDialog.dismiss()
        }
        bottom_sheet_close_btn.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
    }

    fun ConstraintLayout.setHorizontalBias(@IdRes targetViewId: Int, bias: Float) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.setHorizontalBias(targetViewId, bias)
        constraintSet.applyTo(this)
    }
}