package com.corporation8793.fontfolio.library.ocr;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.corporation8793.fontfolio.library.ocr.utils.*;
import com.corporation8793.fontfolio.R;

import java.util.Objects;
import java.util.Random;


/**
 * 캡처 결과 화면 (식별)
 * Created by Administrator on 2016/12/10.
 */

public class ShowCropperedActivity extends AppCompatActivity {

    private              Context context;
    // sd 카드 경로
    private static       String  LANGUAGE_PATH = "";
    // 언어 식별 (assets\tessdata\ 경로의 OCR 파일명)
    private static final String  LANGUAGE      = "eng";

    private static final String    TAG = "ShowCropperedActivity";
    private              ImageView imageView;
    private              ImageView imageView2;
    private              TextView  textView;
    private              TextView  textView2;

    private int    width;
    private int    height;
    private Uri    uri;
    private String result;

    private TessBaseAPI    baseApi = new TessBaseAPI();
    private Handler        handler = new Handler();
    private ProgressDialog dialog;

    private ColorMatrix colorMatrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_croppered);
        context = this;
        LANGUAGE_PATH = getExternalFilesDir("") + "/";
        Log.e("---------", LANGUAGE_PATH);

        width = getIntent().getIntExtra("width", 0);
        height = getIntent().getIntExtra("height", 0);
        uri = getIntent().getData();

        initView();
        initTess();
    }

    private void initView() {
        imageView = findViewById(R.id.image);
        imageView2 = findViewById(R.id.image2);
        textView = findViewById(R.id.text);
        textView2 = findViewById(R.id.text_2);

        dialog = new ProgressDialog(context);
        dialog.setMessage("OCR 판독 중 ...");
        dialog.setCancelable(false);
        dialog.show();

        if (width != 0 && height != 0) {
            int screenWidth = Utils.getWidthInPx(this);
            float scale = (float) screenWidth / (float) width;
            final ViewGroup.LayoutParams lp = imageView.getLayoutParams();
            int imgHeight = (int) (scale * height);
            lp.height = imgHeight;
            imageView.setLayoutParams(lp);
            Log.e(TAG, "imageView.getLayoutParams().width:" + imageView.getLayoutParams().width);
        }
        imageView.setImageURI(uri);
    }

    private void initTess() {
        // 라이브러리 초기화
        baseApi.init(LANGUAGE_PATH, LANGUAGE);

        // 라이브러리 상세 설정
        baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO);
        Thread myThread = new Thread(runnable);
        myThread.start();
    }


    /**
     * 비트맵 URI
     */
    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            // URI 가 있는 사진 로딩
            return MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (Exception e) {
            Log.e("[Android]", e.getMessage());
            Log.e("[Android]", "디렉터리：" + uri);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 그레이스케일 처리
     */
    public Bitmap convertGray(Bitmap bitmap3) {
        colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);

        Paint paint = new Paint();
        paint.setColorFilter(filter);
        Bitmap result = Bitmap.createBitmap(bitmap3.getWidth(), bitmap3.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);

        canvas.drawBitmap(bitmap3, 0, 0, paint);
        return result;
    }

    /**
     * 이진화 처리
     *
     * @param tmp 이진화 최대값, 기본 100
     */
    private Bitmap binaryzation(Bitmap bitmap22, int tmp) {
        // 사진의 너비와 높이 가져오기
        int width = bitmap22.getWidth();
        int height = bitmap22.getHeight();
        // 이진화 이미지 생성
        Bitmap bitmap;
        bitmap = bitmap22.copy(Bitmap.Config.ARGB_8888, true);
        // 원본 그림의 픽셀값 -> 바이너리 값으로 처리
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // 현재 픽셀 값 받기
                int pixel = bitmap.getPixel(i, j);
                // 알파 채널의 값
                int alpha = pixel & 0xFF000000;
                // R
                int red = (pixel & 0x00FF0000) >> 16;
                // G
                int green = (pixel & 0x0000FF00) >> 8;
                // B
                int blue = pixel & 0x000000FF;

                if (red > tmp) {
                    red = 255;
                } else {
                    red = 0;
                }
                if (blue > tmp) {
                    blue = 255;
                } else {
                    blue = 0;
                }
                if (green > tmp) {
                    green = 255;
                } else {
                    green = 0;
                }

                // 가중평균 알고리즘 : 최적의 픽셀 값 계산
                int gray = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                // 흑백 사진으로 변환
                if (gray <= 95) {
                    gray = 0;
                } else {
                    gray = 255;
                }
                // 새로운 픽셀 값
                int newPiexl = alpha | (gray << 16) | (gray << 8) | gray;
                // 새 이미지의 픽셀 설정
                bitmap.setPixel(i, j, newPiexl);
            }
        }
        return bitmap;
    }

    /**
     * OCR 스레드
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            final Bitmap bitmap_1 = convertGray(Objects.requireNonNull(getBitmapFromUri(uri)));

            baseApi.setImage(bitmap_1);
            result = baseApi.getUTF8Text();
            baseApi.end();

            handler.post(() -> {
                imageView2.setImageBitmap(bitmap_1);
                textView.setText(result);
                textView2.setText(randText());
                dialog.dismiss();
            });
        }
    };

    /**
     * OCR 이후 폰트 느낌을 랜덤으로 출력
     */
    public String randText() {
        String[] strings = {
                "사랑스러움", "귀여움", "짜릿함", "매력적임",
                "뜨거움", "냉철함", "강단있음", "부드러움",
                "행복함", "섹시함", "맛있음", "웅장함",
                "상쾌함", "기분 나쁨", "찝찝함", "무서움"
        };
        String s_temp = "";
        String res = "";

        Random random = new Random();
        int j = random.nextInt(7 + 1);

        for (int i=0; i <= j; i++) {
            s_temp = strings[random.nextInt(strings.length)];

            if (!res.contains(s_temp)) {
                res += s_temp + ", ";
            }
        }
        res = res.substring(0, res.length() - 2);

        return res;
    }
}