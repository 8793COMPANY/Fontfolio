package com.corporation8793.fontfolio;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class LoadingActivity extends AppCompatActivity {
    VideoView loading_video;
    private View decorView;
    private int	uiOption;
    ProgressBar progressBar;
    Handler handler;
    int cnt = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        decorView = getWindow().getDecorView();
        uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility( uiOption );

        loading_video = findViewById(R.id.loading_video);
        progressBar = findViewById(R.id.progressbar);

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/loading_video");
        loading_video.setVideoURI(uri);
        loading_video.setOnPreparedListener(mp -> {
            mp.start();
        });

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(cnt<100){
                    cnt++;
                    progressBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                    progressBar.setProgress(cnt);
                    LoadingActivity.this.sendMessage();
                }else{
                    handler.removeCallbacksAndMessages(null);
                }
            }
        };

        sendMessage();

    }

    public void sendMessage(){
        Message message = new Message(); //0.01초에 한번씩 Handler로 메세지를 전송
         handler.sendMessageDelayed(message,10);
    }

}