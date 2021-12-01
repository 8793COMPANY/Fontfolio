package com.corporation8793.fontfolio.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.corporation8793.fontfolio.LoadingActivity;
import com.corporation8793.fontfolio.R;
import com.corporation8793.fontfolio.SelectFontStyleActivity;

public class LoginActivity extends AppCompatActivity {
    LinearLayout login_btn;

    View login_section;
    LinearLayout kakao_login_btn, facebook_login_btn, google_login_btn;
    TextView login_type_devide_section, pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_btn = findViewById(R.id.login_btn_area);
        login_section = findViewById(R.id.login_section);

        kakao_login_btn = findViewById(R.id.kakao_login_area);
        facebook_login_btn = findViewById(R.id.facebook_login_area);
        google_login_btn = findViewById(R.id.google_login_area);

        pw = findViewById(R.id.pw);

        login_type_devide_section = findViewById(R.id.login_type_devide_section);

        login_btn.setOnClickListener(v_->{



        });


        kakao_login_btn.setVisibility(View.INVISIBLE);
        facebook_login_btn.setVisibility(View.INVISIBLE);
        google_login_btn.setVisibility(View.INVISIBLE);
        login_type_devide_section.setVisibility(View.INVISIBLE);

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                ConstraintLayout.LayoutParams lparams = (ConstraintLayout.LayoutParams) pw.getLayoutParams();
                lparams.verticalBias = 0.17f;
                pw.setLayoutParams(lparams);
            }
        };
        a.setDuration(8); // in ms
        a.setFillAfter(true);
        pw.startAnimation(a);

        Animation middleAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_splash_textview);
        login_section.startAnimation(middleAnim);
        middleAnim.setAnimationListener(aniListener01);

        Animation middleAnim2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_splash_welcome_text);
        login_btn.startAnimation(middleAnim2);
        middleAnim.setAnimationListener(aniListener02);



    }

    Animation.AnimationListener aniListener01 = new Animation.AnimationListener() {
        public void onAnimationEnd(Animation animation) {
//            logo_img.setVisibility(View.VISIBLE);
//            Intent intent = new Intent(LoginActivity.this, AppLoginActivity.class);
//            startActivity(intent);
//            finish();
//            overridePendingTransition(0, 0);

        }

        @Override
        public void onAnimationRepeat(Animation animation) { }
        @Override
        public void onAnimationStart(Animation animation) {

        }
    };

    Animation.AnimationListener aniListener02 = new Animation.AnimationListener() {
        public void onAnimationEnd(Animation animation) {
//            logo_img.setVisibility(View.VISIBLE);

            handler.sendEmptyMessageDelayed(0,0);

        }

        @Override
        public void onAnimationRepeat(Animation animation) { }
        @Override
        public void onAnimationStart(Animation animation) {

        }
    };

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0 :
                    finish();
                    Intent intent = new Intent(LoginActivity.this, AppLoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);

//                    welcome_login_text.setVisibility(View.VISIBLE);

                    break ;
                case 1 :
                    break ;
                // TODO : add case.
            }
        }
    } ;
}