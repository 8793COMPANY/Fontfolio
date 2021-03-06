package com.corporation8793.fontfolio;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class CustomView extends ConstraintLayout {
    private ImageView background, stroke;
    private boolean mSelected = false; // 선택된 번호
    public CustomView(@NonNull Context context) {
        super(context);
        initializeViews(context, null);
    }

    public CustomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, null);
    }

    public CustomView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context, null);
    }

    public CustomView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeViews(context, null);
    }


    // 객체 초기화 될때 호출 된다.
    private void initializeViews(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.board_image_view, this);
        if (attrs != null) {
            //attrs.xml에 정의한 스타일을 가져온다 즉 (attrs.xml에서 발생된 selected 속성이
            // 발생되어 private void setSelected(int select, boolean force)를 호출하게 된다
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyCustomView);
            Log.e("a count",a.length()+"");
            mSelected = a.getBoolean(0, false);
            a.recycle(); // 이용이 끝났으면 recycle() 호출
        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        background = (ImageView) findViewById(R.id.background);
        stroke = (ImageView) findViewById(R.id.select_stroke);
        // 처음에는 XML의 지정을 반영하고자 0번째 인수인 force를 true로 한다
        setSelected(mSelected, true);
    }


    public void setSelected(boolean select) {
        setSelected(select, false);
    }

    public boolean getSelected() {
        return mSelected;
    }

    private void setSelected(boolean select, boolean force) {
        mSelected = select;
        if (mSelected) {
            stroke.setBackgroundResource(R.drawable.board_image_btn_background_on);

        } else {
            stroke.setBackgroundResource(R.drawable.board_image_btn_background);

        }
    }
}
