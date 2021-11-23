package com.corporation8793.fontfolio.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.corporation8793.fontfolio.LoadingActivity;
import com.corporation8793.fontfolio.R;
import com.corporation8793.fontfolio.SelectFontStyleActivity;

public class LoginActivity extends AppCompatActivity {
    LinearLayout login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_btn = findViewById(R.id.login_btn_area);

        login_btn.setOnClickListener(v_->{
            Intent intent = new Intent(LoginActivity.this, AppLoginActivity.class);
            startActivity(intent);
        });
    }
}