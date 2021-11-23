package com.corporation8793.fontfolio.login;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.corporation8793.fontfolio.R;
import com.corporation8793.fontfolio.dialog.InitPwBottomDialog;

public class AppLoginActivity  extends AppCompatActivity {

    TextView welcome_login_text;
    EditText email_input_box, pw_input_box;
    LinearLayout login_btn;
    ImageView email_error_msg, pw_error_msg;
    Button input_cancel_btn, visible_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_login);

        welcome_login_text = findViewById(R.id.welcome_login_text);
        login_btn = findViewById(R.id.login_btn_area);
        email_input_box = findViewById(R.id.email_input_box);
        pw_input_box = findViewById(R.id.pw_input_box);

        email_error_msg = findViewById(R.id.email_pw_space);

        input_cancel_btn = findViewById(R.id.email_cancel_btn);

        visible_btn = findViewById(R.id.visible_password);

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

        input_cancel_btn.setOnClickListener(v->{
            email_input_box.setText("");
        });


        email_input_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!email_input_box.getText().toString().trim().equals("")){
                    input_cancel_btn.setVisibility(View.VISIBLE);
                }else{
                    input_cancel_btn.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()){
                    email_error_msg.setBackgroundResource(R.drawable.login_error3);
                }
                else{
                    email_error_msg.setBackgroundResource(R.drawable.login_error3);
                }
            }
        });



        pw_input_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (email_input_box.getText().toString().trim().equals("")){
                    email_error_msg.setBackgroundResource(R.drawable.login_error1);
                }
            }
        });

        visible_btn.setOnClickListener(v->{

        });


    }
}