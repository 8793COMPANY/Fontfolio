package com.corporation8793.fontfolio.activity.ocr;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edmodo.cropper.CropImageView;
import com.corporation8793.fontfolio.library.ocr.camera.*;
import com.corporation8793.fontfolio.library.ocr.utils.*;
import com.corporation8793.fontfolio.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 포토 인터페이스
 * Created by Administrator on 2016/12/8.
 */
public class TakePhoteActivity extends AppCompatActivity implements CameraPreview.OnCameraStatusListener, SensorEventListener {


    private             Context context;
    //true : 가로 화면   false : 세로 화면
    public static final boolean isTransverse = true;

    private static final String TAG       = "TakePhoteActivity";
    public static final  Uri    IMAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    private String PATH;

    private CameraPreview  mCameraPreview;
    private CropImageView  mCropImageView;
    private RelativeLayout mTakePhotoLayout;
    private LinearLayout   mCropperLayout;
    private ImageView      btnClose;
    private ImageView      btnShutter;
    private Button         btnAlbum;
    private ImageView      btnStartCropper;
    private ImageView      btnCloseCropper;


    /**
     * 회전
     */
    private boolean isRotated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 가로 화면 설정
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // 전체 화면 설정
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_take_phote);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        context = this;
        PATH = getExternalCacheDir() + "/AndroidMedia/";

        btnClose = findViewById(R.id.btn_close);
        btnClose.setOnClickListener(onClickListener);
        btnShutter = findViewById(R.id.btn_shutter);
        btnShutter.setOnClickListener(onClickListener);
        btnAlbum = findViewById(R.id.btn_album);
        btnAlbum.setOnClickListener(onClickListener);

        btnStartCropper = findViewById(R.id.btn_startcropper);
        btnStartCropper.setOnClickListener(cropcper);
        btnCloseCropper = findViewById(R.id.btn_closecropper);
        btnCloseCropper.setOnClickListener(cropcper);

        mTakePhotoLayout = findViewById(R.id.take_photo_layout);
        mCameraPreview = findViewById(R.id.cameraPreview);
        FocusView focusView = findViewById(R.id.view_focus);

        mCropperLayout = findViewById(R.id.cropper_layout);
        mCropImageView = findViewById(R.id.CropImageView);
        mCropImageView.setGuidelines(2);

        mCameraPreview.setFocusView(focusView);
        mCameraPreview.setOnCameraStatusListener(this);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isTransverse) {
            if (!isRotated) {
                TextView tvHint = findViewById(R.id.hint);
                ObjectAnimator animator = ObjectAnimator.ofFloat(tvHint, "rotation", 0f, 90f);
                animator.setStartDelay(800);
                animator.setDuration(500);
                animator.setInterpolator(new LinearInterpolator());
                animator.start();

                ImageView btnShutter = findViewById(R.id.btn_shutter);
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(btnShutter, "rotation", 0f, 90f);
                animator1.setStartDelay(800);
                animator1.setDuration(500);
                animator1.setInterpolator(new LinearInterpolator());
                animator1.start();

                View view = findViewById(R.id.crop_hint);
                AnimatorSet animSet = new AnimatorSet();
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "rotation", 0f, 90f);
                ObjectAnimator moveIn = ObjectAnimator.ofFloat(view, "translationX", 0f, -50f);
                animSet.play(animator2).before(moveIn);
                animSet.setDuration(10);
                animSet.start();

                ObjectAnimator animator3 = ObjectAnimator.ofFloat(btnAlbum, "rotation", 0f, 90f);
                animator3.setStartDelay(800);
                animator3.setDuration(500);
                animator3.setInterpolator(new LinearInterpolator());
                animator3.start();
                isRotated = true;
            }
        } else {
            if (!isRotated) {
                View view = findViewById(R.id.crop_hint);
                AnimatorSet animSet = new AnimatorSet();
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "rotation", 0f, 90f);
                ObjectAnimator moveIn = ObjectAnimator.ofFloat(view, "translationX", 0f, -50f);
                animSet.play(animator2).before(moveIn);
                animSet.setDuration(10);
                animSet.start();
                isRotated = true;
            }
        }
        mSensorManager.registerListener(this, mAccel, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.e(TAG, "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 포토 인터페이스
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                // 카메라 끄기
                case R.id.btn_close:
                    finish();
                    break;
                // 사진 찍기
                case R.id.btn_shutter:
                    if (mCameraPreview != null) {
                        mCameraPreview.takePicture();
                    }
                    break;
                // 갤러리 진입
                case R.id.btn_album:
                    Intent intent = new Intent();
                    /* Type 을 image 로 설정 */
                    intent.setType("image/*");

                    /* 인텐트 ACTION_GET_CONTENT */
                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    /* 본 화면으로 돌아가기 */
                    startActivityForResult(intent, 1);
                    break;
            }
        }
    };

    /**
     * 캡처 인터페이스
     */
    private View.OnClickListener cropcper = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_closecropper:
                    Log.e("cropcper", "btn_closecropper");
                    showTakePhotoLayout();
                    break;
                case R.id.btn_startcropper:
                    Log.e("cropcper", "btn_startcropper");
                    // 캡처 그림 가져와서 90도 회전
                    Bitmap cropperBitmap = mCropImageView.getCroppedImage();

                    Bitmap bitmap;
                    bitmap = Utils.rotate(cropperBitmap, -90);

                    // 시간 체크
                    long dateTaken = System.currentTimeMillis();
                    // 사진 파일명
                    String filename = DateFormat.format("yyyy-MM-dd kk.mm.ss", dateTaken).toString() + ".jpg";
                    Uri uri = insertImage(getContentResolver(), filename, dateTaken, PATH, filename, bitmap, null);

                    Intent intent = new Intent(context, ShowCropperedActivity.class);
                    intent.setData(uri);
                    intent.putExtra("path", PATH + filename);
                    intent.putExtra("width", bitmap.getWidth());
                    intent.putExtra("height", bitmap.getHeight());
                    //intent.putExtra("cropperImage", bitmap);
                    Log.e("cropcper", "ShowCropperedActivity");
                    startActivity(intent);
                    bitmap.recycle();
                    finish();
                    break;
            }
        }
    };

    /**
     * 사진 촬영 성공 후 사진 백업 -
     * 사진을 저장하고 캡처 인터페이스를 표시합니다.
     */
    @Override
    public void onCameraStopped(byte[] data) {
        Log.i("TAG", "==onCameraStopped==");
        // 비트맵 생성
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

        if (!isTransverse) {
            bitmap = Utils.rotate(bitmap, 90);
        }
        // 시간 체크
        long dateTaken = System.currentTimeMillis();
        // 사진 파일명
        String filename = DateFormat.format("yyyy-MM-dd kk.mm.ss", dateTaken).toString() + ".jpg";
        // 이미지 저장 (PATH 디렉터리)
        Uri source = insertImage(getContentResolver(), filename, dateTaken, PATH, filename, bitmap, data);

        // 스크린샷 준비
        bitmap = Utils.rotate(bitmap, 90);
        mCropImageView.setImageBitmap(bitmap);
        showCropperLayout();
    }

    /**
     * 이미지 리셋
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                bitmap = Utils.rotate(bitmap, 90);
                mCropImageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
        showCropperLayout();
    }

    /**
     * 이미지를 저장하고 미디어 데이터베이스에 추가
     */
    private Uri insertImage(ContentResolver cr, String name, long dateTaken,
                            String directory, String filename, Bitmap source, byte[] jpegData) {
        OutputStream outputStream = null;
        String filePath = directory + filename;
        try {
            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(directory, filename);
            if (file.createNewFile()) {
                outputStream = new FileOutputStream(file);
                if (source != null) {
                    source.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                } else {
                    outputStream.write(jpegData);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return null;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Throwable t) {
                }
            }
        }
        ContentValues values = new ContentValues(7);
        values.put(MediaStore.Images.Media.TITLE, name);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename);
        values.put(MediaStore.Images.Media.DATE_TAKEN, dateTaken);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.DATA, filePath);
        return cr.insert(IMAGE_URI, values);
    }

    private void showTakePhotoLayout() {
        mTakePhotoLayout.setVisibility(View.VISIBLE);
        mCropperLayout.setVisibility(View.GONE);
    }

    private void showCropperLayout() {
        mTakePhotoLayout.setVisibility(View.GONE);
        mCropperLayout.setVisibility(View.VISIBLE);
        // 카메라 계속 켜기
        mCameraPreview.start();
    }


    private float         mLastX       = 0;
    private float         mLastY       = 0;
    private float         mLastZ       = 0;
    private boolean       mInitialized = false;
    private SensorManager mSensorManager;
    private Sensor        mAccel;


    /**
     * 카메라 오토 포커스 (자동 초점)
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        if (!mInitialized) {
            mLastX = x;
            mLastY = y;
            mLastZ = z;
            mInitialized = true;
        }
        float deltaX = Math.abs(mLastX - x);
        float deltaY = Math.abs(mLastY - y);
        float deltaZ = Math.abs(mLastZ - z);

        if (deltaX > 0.8 || deltaY > 0.8 || deltaZ > 0.8) {
            mCameraPreview.setFocus();
        }
        mLastX = x;
        mLastY = y;
        mLastZ = z;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}