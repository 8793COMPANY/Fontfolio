package com.corporation8793.fontfolio.activity.ocr;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.corporation8793.fontfolio.BuildConfig;
import com.corporation8793.fontfolio.R;
import com.corporation8793.fontfolio.common.Fontfolio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StartOcrActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_CAMERA        = 454;
    private static final int PERMISSIONS_REQUEST_WRITE_STORAGE = 455;

    static final String  PERMISSION_CAMERA        = Manifest.permission.CAMERA;
    static final String  PERMISSION_WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private      Context context;

    public Fontfolio fontfolio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        fontfolio = new Fontfolio();

        checkSelfPermission();

        new Thread(() -> deepFile("tessdata")).start();
    }

    /**
     * assets 에 있는 파일을 복사합니다.
     *
     * @param path
     */
    public void deepFile(String path) {
        String newPath = getExternalFilesDir(null) + "/";
        try {
            String str[] = getAssets().list(path);
            if (str.length > 0) {
                // 디렉터리라면
                File file = new File(newPath + path);
                file.mkdirs();
                for (String string : str) {
                    path = path + "/" + string;
                    deepFile(path);

                    // 원래 path 로 돌아감
                    path = path.substring(0, path.lastIndexOf('/'));
                }
            } else {
                // 파일이 있는 경우
                InputStream is = getAssets().open(path);
                FileOutputStream fos = new FileOutputStream(newPath + path);
                byte[] buffer = new byte[1024];
                while (true) {
                    int len = is.read(buffer);
                    if (len == -1) {
                        break;
                    }
                    fos.write(buffer, 0, len);
                }
                is.close();
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            if (requestCode == PERMISSIONS_REQUEST_CAMERA) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && Environment.isExternalStorageManager()) {
                    fontfolio.moveToActivity(this, TakePhoteActivity.class, true);
                } else {
                    Toast.makeText(context, "권한을 허용해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            if (requestCode == PERMISSIONS_REQUEST_CAMERA || requestCode == PERMISSIONS_REQUEST_WRITE_STORAGE) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fontfolio.moveToActivity(this, TakePhoteActivity.class, true);
                } else {
                    Toast.makeText(context, "권한을 허용해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    void checkSelfPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                finish();
                Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
                Toast.makeText(context, "권한을 허용해주세요", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri));
                finish();
            } else {
                if (ContextCompat.checkSelfPermission(this, PERMISSION_CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{PERMISSION_CAMERA}, PERMISSIONS_REQUEST_CAMERA);
                } else {
                    fontfolio.moveToActivity(this, TakePhoteActivity.class, true);
                }
            }
        }else {
            if (ContextCompat.checkSelfPermission(this, PERMISSION_CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{PERMISSION_CAMERA}, PERMISSIONS_REQUEST_CAMERA);
            } else if (ContextCompat.checkSelfPermission(this, PERMISSION_WRITE_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{PERMISSION_WRITE_STORAGE}, PERMISSIONS_REQUEST_WRITE_STORAGE);
            } else {
                fontfolio.moveToActivity(this, TakePhoteActivity.class, true);
            }
        }
    }
}