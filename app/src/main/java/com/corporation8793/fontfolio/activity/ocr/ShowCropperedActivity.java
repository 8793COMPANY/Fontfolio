package com.corporation8793.fontfolio.activity.ocr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.corporation8793.fontfolio.Classifier;
import com.corporation8793.fontfolio.analysis.AnalysisItem;
import com.corporation8793.fontfolio.analysis.FontAnalysisAdapter;
import com.corporation8793.fontfolio.board.BoardItem;
import com.corporation8793.fontfolio.common.Fontfolio;
import com.corporation8793.fontfolio.library.room.entity.font.Font;
import com.corporation8793.fontfolio.library.room.entity.font.FontClassification;
import com.corporation8793.fontfolio.library.room.entity.font.FontStyleInformation;
import com.corporation8793.fontfolio.recylcerview.FontItem;
import com.googlecode.tesseract.android.TessBaseAPI;
import com.corporation8793.fontfolio.library.ocr.utils.*;
import com.corporation8793.fontfolio.R;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.FormatFlagsConversionMismatchException;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;


/**
 * ?????? ?????? ?????? (??????)
 * Created by Administrator on 2016/12/10.
 */

public class ShowCropperedActivity extends AppCompatActivity {

    private              Context context;
    private              Activity activity;
    // sd ?????? ??????
    private static       String  LANGUAGE_PATH = "";
    // ?????? ?????? (assets\tessdata\ ????????? OCR ?????????)
    private static final String  LANGUAGE      = "eng";

    private static final String    TAG = "ShowCropperedActivity";
    private              ImageView imageView;
    private              TextView  textView;
    private              Button    back_btn;
    private              Button    info_btn;
    private              RecyclerView font_analysis_result;

    private              Font superiorFont;
    private              TextView fc_badge_text;
    private              TextView fs_badge_text;
    private              TextView fsi_badge_text;
    private              LinearLayout ofl_badge;
    private              LinearLayout paid_badge;

    private              Resources res;
    private              String pkg;

    private int    width;
    private int    height;
    private Uri    uri;
    private String result;

    private TessBaseAPI    baseApi = new TessBaseAPI();
    private Handler        handler = new Handler();
    private ProgressDialog dialog;

    private ColorMatrix colorMatrix;

    private Classifier classifier;

    FontAnalysisAdapter adapter;
    ArrayList<AnalysisItem> mList = new ArrayList<AnalysisItem>();

//    String [] fontNames = {"Days","Fava-black","Gidole-Regular","Leaner-Thin","MoonLight","No-move","Nurom-Bold","SPACE","STAY HOME","TitanOne-Regular","ViceCitySans"};
//    String [ ] fontNames = {
//        "Adobe Caslon Pro-Regular",
//        "Baskerville-Regular",
//        "Bembo Std-Regular",
//        "Centaur-Regular",
//        "Cormorant SC-Regular",
//        "david_libre_regular",
//        "EB Garamond-Regular",
//        "gentium_regular",
//        "Gill Sans Std-Regular",
//        "Mont-Regular"};

//    String [ ] fontNames = {
//            "Alegreya-Regular",
//            "Epilogue-Regular",
//            "Inter-Regular",
//            "David Libre-Regular",
//            "Gentium-Regular",
//            "Adobe Caslon Pro-Regular",
//            "Bembo Std-Regular",
//            "Centaur-Regular",
//            "Mont-Regular",
//            "Baskerville-Regular"};

    String [ ] fontNames = {
            "Adobe Caslon Pro-Regular",
            "Alegreya-Regular",
            "Baskerville-Regular",
            "Bembo Std-Regular",
            "Centaur-Regular",
            "David Libre-Regular",
            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_croppered);
        context = this;
        activity = this;
        LANGUAGE_PATH = getExternalFilesDir("") + "/";
        Log.e("---------", LANGUAGE_PATH);

        width = getIntent().getIntExtra("width", 0);
        height = getIntent().getIntExtra("height", 0);
        uri = getIntent().getData();
        Log.e(TAG, "get uri info : " + uri);

        initView();
        initTess();
        initClassifier();

        res = getResources();
        pkg = getPackageName();

//        Fontfolio.list.parallelStream().filter(p->p.getFontName().equals(mData.get(position).name)).findAny().get().getFontCopyrightHolder()


//        String outString = String.format(Locale.ENGLISH, "%d, %.0f%%", result.first, result.second * 100.0f);
    }

    private void initView() {
        imageView = findViewById(R.id.image);
        textView = findViewById(R.id.text);
        back_btn = findViewById(R.id.back_btn);
        info_btn = findViewById(R.id.info_btn);
        font_analysis_result = findViewById(R.id.analysis_result);


        fc_badge_text = findViewById(R.id.fc_badge_text);
        fs_badge_text = findViewById(R.id.fs_badge_text);
        fsi_badge_text = findViewById(R.id.fsi_badge_text);
        ofl_badge = findViewById(R.id.ofl_badge);
        paid_badge = findViewById(R.id.paid_badge);

        back_btn.setOnClickListener(v -> finish());

        dialog = new ProgressDialog(context);
        dialog.setMessage("OCR ?????? ??? ...");
        dialog.setCancelable(false);
        dialog.show();

        imageView.setImageURI(uri);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);


    }

    private void initTess() {
        // ??????????????? ?????????
        baseApi.init(LANGUAGE_PATH, LANGUAGE);

        // ??????????????? ?????? ??????
        baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO);
        Thread myThread = new Thread(runnable);
        myThread.start();
    }

    private void initRecyclerView(){
        adapter = new FontAnalysisAdapter(mList, result, res, pkg);
        font_analysis_result.setAdapter(adapter);
        font_analysis_result.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        font_analysis_result.addItemDecoration(dividerItemDecoration);
    }


    /**
     * ????????? URI
     */
    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            // URI ??? ?????? ?????? ??????
            return MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (Exception e) {
            Log.e("[Android]", e.getMessage());
            Log.e("[Android]", "???????????????" + uri);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ?????????????????? ??????
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
     * ????????? ??????
     *
     * @param tmp ????????? ?????????, ?????? 100
     */
    private Bitmap binaryzation(Bitmap bitmap22, int tmp) {
        // ????????? ????????? ?????? ????????????
        int width = bitmap22.getWidth();
        int height = bitmap22.getHeight();
        // ????????? ????????? ??????
        Bitmap bitmap;
        bitmap = bitmap22.copy(Bitmap.Config.ARGB_8888, true);
        // ?????? ????????? ????????? -> ???????????? ????????? ??????
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // ?????? ?????? ??? ??????
                int pixel = bitmap.getPixel(i, j);
                // ?????? ????????? ???
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

                // ???????????? ???????????? : ????????? ?????? ??? ??????
                int gray = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                // ?????? ???????????? ??????
                if (gray <= 95) {
                    gray = 0;
                } else {
                    gray = 255;
                }
                // ????????? ?????? ???
                int newPiexl = alpha | (gray << 16) | (gray << 8) | gray;
                // ??? ???????????? ?????? ??????
                bitmap.setPixel(i, j, newPiexl);
            }
        }
        return bitmap;
    }

    /**
     * OCR ?????????
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            final Bitmap bitmap_1 = convertGray(Objects.requireNonNull(getBitmapFromUri(uri)));

            baseApi.setImage(bitmap_1);
            result = baseApi.getUTF8Text();
            baseApi.end();

            handler.post(() -> {
                textView.setText(result);
                initRecyclerView();
                dialog.dismiss();
            });
        }
    };

    /**
     * OCR ?????? ?????? ????????? ???????????? ??????
     */
    public String randText() {
        String[] strings = {
                "???????????????", "?????????", "?????????", "????????????",
                "?????????", "?????????", "????????????", "????????????",
                "?????????", "?????????", "?????????", "?????????",
                "?????????", "?????? ??????", "?????????", "?????????"
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


    private void initClassifier() {
        classifier = new Classifier(getAssets(), Classifier.DIGIT_CLASSIFIER);
        try {
            Log.e("initClassifier","in");
            classifier.init();

            Log.e("uri!!",uri+"");
            Bitmap bitmapImage = getBitmapFromUri(uri);

            Bitmap bitmap = Bitmap.createScaledBitmap(bitmapImage, 128, 128, true);
            float [] arrays  = classifier.classify(bitmap);

            for (int i=0; i<arrays.length; i++){
                AnalysisItem item = new AnalysisItem();
                item.setName(fontNames[i]);
                item.setPercent(String.format("%.2f", arrays[i]*100));
                mList.add(item);
            }

            Collections.sort(mList, (b1, b2) -> b2.getPercent().compareTo(b1.getPercent()));

            Log.e("initClassifier","end");

            superiorFont = Fontfolio.list.stream()
                    .filter(font -> font.getFontName().equals(mList.get(0).getName()))
                    .findAny()
                    .orElse(null);

            Log.e("superiorFont","" + superiorFont.getFontName());

            fontClassification();
        } catch (Exception e) {
            Log.d("initClassifier", "IOException");
        }
    }

    private void fontClassification() {
        if (superiorFont != null) {
            FontClassification fontClassification = superiorFont.getFontClassification();
            String fontStyle = superiorFont.getFontStyle();
            FontStyleInformation fontStyleInformation = superiorFont.getFontStyleInformation();
            Boolean isOFL = superiorFont.getFontLicense().getOFL();

            // fontClassification
            if (fontClassification.getSerif()) {
                fc_badge_text.setText("Serif");
            } else if (fontClassification.getSans_Serif()) {
                fc_badge_text.setText("Sans Serif");
            } else if (fontClassification.getSlab_Serif()) {
                fc_badge_text.setText("Slab Serif");
            } else if (fontClassification.getDisplay()) {
                fc_badge_text.setText("Display");
            } else if (fontClassification.getHand_Written()) {
                fc_badge_text.setText("Hand Written");
            } else if (fontClassification.getCalligraphy()) {
                fc_badge_text.setText("Calligraphy");
            } else if (fontClassification.getScript()) {
                fc_badge_text.setText("Script");
            } else {
                fc_badge_text.setText("Unknown Class");
            }

            // fontStyle
            fs_badge_text.setText(fontStyle);

            // fontStyleInformation
            if (fontStyleInformation.getOriginal()) {
                fsi_badge_text.setText("Original");
            } else if (fontStyleInformation.getNormal()) {
                fsi_badge_text.setText("Normal");
            } else {
                fsi_badge_text.setText("Unknown Style Information");
            }

            // isOFL
            if (isOFL) {
                paid_badge.setVisibility(View.GONE);
            } else {
                ofl_badge.setVisibility(View.GONE);
            }
        }
    }
}