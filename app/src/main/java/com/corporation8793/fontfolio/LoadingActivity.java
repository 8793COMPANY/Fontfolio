package com.corporation8793.fontfolio;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class LoadingActivity extends AppCompatActivity {
    VideoView loading_video;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        loading_video = findViewById(R.id.loading_video);

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/loading_video");
        loading_video.setVideoURI(uri);
    }
}
