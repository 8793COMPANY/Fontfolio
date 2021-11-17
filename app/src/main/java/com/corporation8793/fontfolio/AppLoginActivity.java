package com.corporation8793.fontfolio;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AppLoginActivity  extends AppCompatActivity {

    TextView welcome_login_text;
    EditText email_input_box;
    LinearLayout login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_login);

        welcome_login_text = findViewById(R.id.welcome_login_text);
        login_btn = findViewById(R.id.login_btn_area);
        email_input_box = findViewById(R.id.email_input_box);

        String content = welcome_login_text.getText().toString();
        SpannableString spannableString = new SpannableString(content);

        String word = "Welcome :)";
        int start = content.indexOf(word);
        int end = start + word.length();

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.3f), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        welcome_login_text.setText(spannableString);

        final InitPwBottomDialog initPwBottomDialog = new InitPwBottomDialog("boxerlady@naver.com");




        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPwBottomDialog.show(getSupportFragmentManager(),initPwBottomDialog.getTag());
            }
        });

        InputMethodManager manager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        manager.showSoftInput(email_input_box, InputMethodManager.SHOW_IMPLICIT);
    }
}