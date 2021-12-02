package com.corporation8793.fontfolio.login;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.corporation8793.fontfolio.R;
import com.corporation8793.fontfolio.SelectFontStyleActivity;
import com.corporation8793.fontfolio.activity.MainActivity;
import com.corporation8793.fontfolio.common.Fontfolio;
import com.corporation8793.fontfolio.dialog.InitPwBottomDialog;

public class AppLoginActivity  extends AppCompatActivity {

    TextView welcome_login_text, login_btn_text;
    EditText email_input_box, pw_input_box;
    LinearLayout login_btn;
    ImageView email_error_msg, pw_error_msg;
    Button input_cancel_btn, visible_btn, back_btn;




    boolean check = false;

    View login_section;

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

        pw_error_msg = findViewById(R.id.login_section_bottom);

        login_btn_text = findViewById(R.id.login_btn_text);

        back_btn = findViewById(R.id.back_btn);
        login_section = findViewById(R.id.login_section);

//        Animation middleAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_splash_textview);
//
//        login_section.startAnimation(middleAnim);
//
//        middleAnim.setAnimationListener(aniListener01);
//
//        handler.sendEmptyMessageDelayed(0,250);








        email_input_box.setFocusable(View.FOCUSABLE);
        String content = welcome_login_text.getText().toString();
        SpannableString spannableString = new SpannableString(content);

        String word = "Welcome :)";
        int start = content.indexOf(word);
        int end = start + word.length();

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.3f), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        welcome_login_text.setText(spannableString);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(email_input_box.getApplicationWindowToken(), 0);

        final InitPwBottomDialog initPwBottomDialog = new InitPwBottomDialog("boxerlady@naver.com");



//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//
//            }
//        },3000);



        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Fontfolio.prefs.getBoolean("first_run",true)){
                    Fontfolio.prefs.setBoolean("first_run",false);
                    Intent intent = new Intent(AppLoginActivity.this, SelectFontStyleActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(AppLoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        });

        input_cancel_btn.setOnClickListener(v->{
            email_input_box.setText("");
            check = false;
            login_btn.setBackgroundTintList((ColorStateList.valueOf(Color.parseColor("#0D000000"))));
            login_btn_text.setTextColor(Color.parseColor("#80000000"));
        });


        email_input_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                check_login_enabled();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()){
                    email_error_msg.setVisibility(View.VISIBLE);
                    email_error_msg.setBackgroundResource(R.drawable.login_error3);
                }
                else{
                    email_error_msg.setBackgroundResource(0);
                    check = true;
                }

                if (!email_input_box.getText().toString().trim().equals("")){
                    input_cancel_btn.setVisibility(View.VISIBLE);
                }else{
                    input_cancel_btn.setVisibility(View.INVISIBLE);
                    email_error_msg.setVisibility(View.INVISIBLE);
                }
            }
        });



        pw_input_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                check_login_enabled();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (email_input_box.getText().toString().trim().equals("")){
                    email_error_msg.setBackgroundResource(R.drawable.login_error1);
                }
            }
        });

        visible_btn.setOnClickListener(v->{
            if (visible_btn.isSelected()){
                visible_btn.setBackgroundResource(R.drawable.login_view_pw_off_btn);
                pw_input_box.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                visible_btn.setSelected(false);
            }else{
                visible_btn.setBackgroundResource(R.drawable.login_view_pw_on_btn);
                pw_input_box.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                visible_btn.setSelected(true);
            }
        });

        pw_error_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPwBottomDialog.show(getSupportFragmentManager(),initPwBottomDialog.getTag());
            }
        });

        back_btn.setOnClickListener(v->{
            finish();
        });

    }

    void check_login_enabled(){
        if(check && !pw_input_box.getText().toString().trim().equals("") ){
            login_btn.setBackgroundTintList((ColorStateList.valueOf(Color.parseColor("#dd0000"))));
            login_btn_text.setTextColor(getColor(R.color.white));
        }else{
            login_btn.setBackgroundTintList((ColorStateList.valueOf(Color.parseColor("#0D000000"))));
            login_btn_text.setTextColor(Color.parseColor("#80000000"));

        }
    }

    Animation.AnimationListener aniListener01 = new Animation.AnimationListener() {
        public void onAnimationEnd(Animation animation) {
//            logo_img.setVisibility(View.VISIBLE);

        }

        @Override
        public void onAnimationRepeat(Animation animation) { }
        @Override
        public void onAnimationStart(Animation animation) { }
    };

    Animation.AnimationListener aniListener02 = new Animation.AnimationListener() {
        public void onAnimationEnd(Animation animation) {
//            logo_img.setVisibility(View.VISIBLE);

        }

        @Override
        public void onAnimationRepeat(Animation animation) { }
        @Override
        public void onAnimationStart(Animation animation) { }
    };

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0 :
                    welcome_login_text.setVisibility(View.VISIBLE);
                    Animation middleAnim2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_splash_welcome_text);
                    welcome_login_text.startAnimation(middleAnim2);
                    middleAnim2.setAnimationListener(aniListener02);

//                    welcome_login_text.setVisibility(View.VISIBLE);

                    break ;
                case 1 :
                    break ;
                // TODO : add case.
            }
        }
    } ;
}

