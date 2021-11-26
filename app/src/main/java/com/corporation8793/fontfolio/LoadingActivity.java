package com.corporation8793.fontfolio;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.corporation8793.fontfolio.activity.MainActivity;

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
            handler.sendEmptyMessageDelayed(1,1000);
        });

        loading_video.setOnCompletionListener(mp -> {
            Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
            startActivity(intent);
        });


        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        if(cnt<200){
                            cnt++;
                            progressBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                            progressBar.setProgress(cnt);
                            LoadingActivity.this.sendProgressMessage();
                        }else{
                            handler.removeCallbacksAndMessages(null);
                        }

                        break;

                    case 1:
                        sendProgressMessage();
                        break;


                }

            }
        };



    }

    public void sendProgressMessage(){
        Message message = new Message(); //0.01초에 한번씩 Handler로 메세지를 전송
         handler.sendMessageDelayed(message,10);
    }

}
