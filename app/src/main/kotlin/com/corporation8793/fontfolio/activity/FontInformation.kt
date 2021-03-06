package com.corporation8793.fontfolio.activity

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.corporation8793.fontfolio.R
import com.corporation8793.fontfolio.board.CreateBoardActivity
import com.corporation8793.fontfolio.board.SaveBoardActivity
import com.corporation8793.fontfolio.common.Fontfolio

class FontInformation : AppCompatActivity() {
    lateinit var search_bar_input : TextView
    lateinit var search_bar_back_btn : LinearLayout
    lateinit var search_bar_div : ConstraintLayout
    lateinit var font_title : TextView
    lateinit var font_sub_title : TextView
    lateinit var font_sub_style : TextView
    lateinit var font_heart_count : TextView
    lateinit var font_view_count : TextView
    lateinit var font_info_heart : ImageView
    lateinit var font_info_add_btn : ImageView
    lateinit var font_preview : EditText
    lateinit var fc_badge_text : TextView
    lateinit var fs_badge_text : TextView
    lateinit var fsi_badge_text : TextView
    lateinit var ofl_badge : LinearLayout
    lateinit var paid_badge : LinearLayout
    lateinit var copyright : TextView
    lateinit var license : TextView
    lateinit var download_link : TextView

    lateinit var desc : WebView
    lateinit var desc_result : TextView
    var heart_check = false

    override fun onDestroy() {
        super.onDestroy()
        if (!Fontfolio.searchFragment.isDirectFromHomeFragment) {
            Fontfolio.searchFragment.showSoftKeyboard()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_font_information)

        search_bar_input = findViewById(R.id.search_bar_input)
        search_bar_back_btn = findViewById(R.id.search_bar_back_btn)
        search_bar_div = findViewById(R.id.search_bar_div)
        font_title = findViewById(R.id.font_title)
        font_sub_title = findViewById(R.id.font_sub_title)
        font_sub_style = findViewById(R.id.font_sub_style)
        font_heart_count = findViewById(R.id.font_heart_count)
        font_view_count = findViewById(R.id.font_view_count)
        font_info_heart = findViewById(R.id.font_info_heart)
        var font_info_heart_state = false
        font_info_add_btn = findViewById(R.id.font_info_add_btn)
        font_preview = findViewById(R.id.font_preview)
        fc_badge_text = findViewById(R.id.fc_badge_text)
        fs_badge_text = findViewById(R.id.fs_badge_text)
        fsi_badge_text = findViewById(R.id.fsi_badge_text)
        ofl_badge = findViewById(R.id.ofl_badge)
        paid_badge = findViewById(R.id.paid_badge)
        copyright = findViewById(R.id.copyright)
        license = findViewById(R.id.license)
        download_link = findViewById(R.id.download_link)

        desc = findViewById(R.id.desc)
        desc_result = findViewById(R.id.desc_result)

        val font = Fontfolio.list.filter { font -> font.fontName == intent.getStringExtra("fontName") }[0]

        font_info_heart.setOnClickListener {
            font_info_heart_state = !font_info_heart_state

            if (font_info_heart_state) {
                // ON
                font_info_heart.setImageDrawable(resources.getDrawable(R.drawable.heart_on, theme))
            } else {
                // OFF
                font_info_heart.setImageDrawable(resources.getDrawable(R.drawable.font_info_heart_off, theme))
            }
        }

        search_bar_back_btn.setOnClickListener {
            if (!Fontfolio.searchFragment.isDirectFromHomeFragment) {
                Fontfolio.searchFragment.search_bar_input.text.clear()
            }

            finish()
        }

        font_info_heart.setOnClickListener({
            if (heart_check) {
                font_info_heart.setImageResource(R.drawable.font_info_heart_off)
                heart_check = false
            }else{
                font_info_heart.setImageResource(R.drawable.font_info_heart_on)
                heart_check = true
            }
        })

        search_bar_div.setOnClickListener {
            if (!Fontfolio.searchFragment.isDirectFromHomeFragment) {
                Fontfolio.searchFragment.search_bar_input.setText(font.fontName)
                Fontfolio.searchFragment.search_bar_input.setSelection(font.fontName.length)
                Fontfolio.searchFragment.search_bar_input_cancel.setOnClickListener {
                    Fontfolio.searchFragment.activity.apply {
                        startActivity(Intent(this, FontInformation().javaClass)
                            .putExtra("fontName", font.fontName))
                    }
                }
            } else {
            }

            finish()
        }

        search_bar_input.text = font.fontName

        font_title.text = font.fontName

        font_sub_title.text = if (font.fontName.contains(" ")){
            font.fontName.substringBefore(" ")
        } else {
            font.fontName.substringBefore("-")
        }
        Log.e("font_sub_title", font_sub_title.text.toString())

        font_sub_style.text = "${Fontfolio.list.count {
                it.fontName.contains(font_sub_title.text.toString()) }} styles"

        font_info_add_btn.setOnClickListener {
            var intent :Intent = Intent(Fontfolio.searchFragment.activity,CreateBoardActivity::class.java)
            intent.putExtra("fontName",font.fontName)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
//            Fontfolio().moveToActivity(Fontfolio.searchFragment.activity, CreateBoardActivity::class.java, false)
        }
        if (this.resources.getIdentifier(
                "${font.fontName.toLowerCase()
                    .replace("-", "_")
                    .replace(" ", "_")}",
                "font", this.packageName) == 0) {
            Log.e("changeFontOfTextView", "${font.fontName.toLowerCase()
                .replace("-", "_")
                .replace(" ", "_")} Font File Not Found !! :(")
            font_preview.visibility = View.GONE
        } else {
            Fontfolio().changeFontOfTextView(this, font_preview, font.fontName)
            //font_preview.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        }

        font.fontClassification.apply {
            fc_badge_text.text = when {
                Serif -> "Serif"
                Sans_Serif -> "Sans Serif"
                Slab_Serif -> "Slab Serif"
                Display -> "Display"
                Hand_Written -> "Hand Written"
                Calligraphy -> "Calligraphy"
                Script -> "Script"
                else -> "Unknown Class"
            }
        }

        fs_badge_text.text = font.fontStyle

        font.fontStyleInformation.apply {
            fsi_badge_text.text = when {
                Original -> "Original"
                Normal -> "Normal"
                else -> "Unknown Style Information"
            }
        }

        when (font.fontLicense.OFL) {
            true -> paid_badge.visibility = View.GONE
            false -> ofl_badge.visibility = View.GONE
        }
        license.text = "License : ${font.fontLicenseDescription}"
        copyright.text = "Copyright : ${font.fontCopyrightHolder}"

        download_link.text = font.fontDownloadLink
        download_link.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        download_link.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(font.fontDownloadLink)))
        }

        desc.settings.javaScriptEnabled = true
        desc.loadUrl(font.fontDownloadLink)
        desc.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                view?.evaluateJavascript("(function() { if(document.getElementsByClassName('specimen__about-description')[0] != null){ return (document.getElementsByClassName('specimen__about-description')[0].innerText)}; })();")
                { html ->
                    //Log.e("raw html", html)
                    if (html != "null") {
                        val result = html.replace("\\n", "\n")
                            .replace("\\\"", "\"")
                        //Log.e("replace html", result)
                        desc_result.text = result
                        desc_result.visibility = View.VISIBLE
                    } else {
                        desc_result.text = "Font Description Not Found.\nPlease Try Again Later. :("
                        desc_result.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (!Fontfolio.searchFragment.isDirectFromHomeFragment) {
            Fontfolio.searchFragment.search_bar_input.text.clear()
        }
        finish()
    }
}