package com.corporation8793.fontfolio.activity

import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.corporation8793.fontfolio.R
import com.corporation8793.fontfolio.common.Fontfolio


class FontInformation : AppCompatActivity() {
    lateinit var search_bar_input : EditText
    lateinit var test : TextView
    lateinit var desc : WebView
    lateinit var desc_result : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_font_information)

        search_bar_input = findViewById(R.id.search_bar_input)
        test = findViewById(R.id.test)
        desc = findViewById(R.id.desc)
        desc_result = findViewById(R.id.desc_result)

        val font = Fontfolio.list.filter { font -> font.fontName == intent.getStringExtra("fontName") }[0]

        search_bar_input.setText(font.fontName)
        test.text = font.fontName

        desc.settings.javaScriptEnabled = true
        desc.loadUrl(font.fontDownloadLink)
        desc.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                view?.evaluateJavascript("(function() { if(document.getElementsByClassName('specimen__about-description')[0] != null){ return (document.getElementsByClassName('specimen__about-description')[0].innerText)}; })();")
                { html ->
                    Log.e("raw html", html)
                    if (html != "null") {
                        val result = html.replace("\\n", "")
                            .replace("\\\"", "")
                        Log.e("replace html", result)
                        desc_result.text = result
                    }
                }
            }
        }
    }
}