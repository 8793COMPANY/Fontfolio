package com.corporation8793.fontfolio.activity

import android.os.Bundle
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
import com.corporation8793.fontfolio.common.Fontfolio


class FontInformation : AppCompatActivity() {
    lateinit var search_bar_input : EditText
    lateinit var search_bar_input_cancel : LinearLayout
    lateinit var search_bar_div : ConstraintLayout
    lateinit var font_title : TextView
    lateinit var font_sub_title : TextView
    lateinit var font_sub_style : TextView
    lateinit var font_heart_count : TextView
    lateinit var font_view_count : TextView
    lateinit var font_info_heart : ImageView
    lateinit var font_info_add_btn : ImageView
    lateinit var font_preview : TextView
    lateinit var fc_badge_text : TextView
    lateinit var fs_badge_text : TextView
    lateinit var fsi_badge_text : TextView
    lateinit var ofl_badge : LinearLayout
    lateinit var paid_badge : LinearLayout
    lateinit var copyright : TextView
    lateinit var license : TextView

    lateinit var desc : WebView
    lateinit var desc_result : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_font_information)

        search_bar_input = findViewById(R.id.search_bar_input)
        search_bar_input_cancel = findViewById(R.id.search_bar_input_cancel)
        search_bar_div = findViewById(R.id.search_bar_div)
        font_title = findViewById(R.id.font_title)
        font_sub_title = findViewById(R.id.font_sub_title)
        font_sub_style = findViewById(R.id.font_sub_style)
        font_heart_count = findViewById(R.id.font_heart_count)
        font_view_count = findViewById(R.id.font_view_count)
        font_info_heart = findViewById(R.id.font_info_heart)
        font_info_add_btn = findViewById(R.id.font_info_add_btn)
        font_preview = findViewById(R.id.font_preview)
        fc_badge_text = findViewById(R.id.fc_badge_text)
        fs_badge_text = findViewById(R.id.fs_badge_text)
        fsi_badge_text = findViewById(R.id.fsi_badge_text)
        ofl_badge = findViewById(R.id.ofl_badge)
        paid_badge = findViewById(R.id.paid_badge)
        copyright = findViewById(R.id.copyright)
        license = findViewById(R.id.license)

        desc = findViewById(R.id.desc)
        desc_result = findViewById(R.id.desc_result)

        val font = Fontfolio.list.filter { font -> font.fontName == intent.getStringExtra("fontName") }[0]

        search_bar_input.setText(font.fontName)
        font_title.text = font.fontName

        font_sub_title.text = if (font.fontName.contains(" ")){
            font.fontName.substringBefore(" ")
        } else {
            font.fontName.substringBefore("-")
        }
        Log.e("font_sub_title", font_sub_title.text.toString())

        font_sub_style.text = "${Fontfolio.list.count {
                it.fontName.contains(font_sub_title.text.toString()) }} styles"

        Fontfolio().changeFontOfTextView(this, font_preview, font.fontName)

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
        copyright.text = font.fontCopyrightHolder
        license.text = font.fontLicenseDescription

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
                    } else {
                        desc_result.text = "Font Description Not Found.\nPlease Try Again Later. :("
                    }
                }
            }
        }
    }
}