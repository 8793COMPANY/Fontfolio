package com.corporation8793.fontfolio;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.corporation8793.fontfolio.login.AppLoginActivity;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class SelectFontStyleActivity extends AppCompatActivity {

    ArrayList<String> font_style_list = new ArrayList<String>();

    LinearLayout next_btn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_font_style);

        next_btn = findViewById(R.id.next_btn);
        listDrawable();

        Display display = getWindowManager().getDefaultDisplay();  // in Activity
        /* getActivity().getWindowManager().getDefaultDisplay() */ // in Fragment
        Point size = new Point();
        display.getRealSize(size); // or getSize(size)
        int width = size.x;
        int height = size.y;

        // 커스텀 아답타 생성
        MyAdapter adapter = new MyAdapter (
                getApplicationContext(),
                R.layout.font_style_item,       // GridView 항목의 레이아웃 row.xml
                font_style_list,
                (int)((width /720.0) * 208));    // 데이터

        GridView gv = (GridView)findViewById(R.id.font_style_list);
        gv.setAdapter(adapter);



        gv.setHorizontalSpacing((int)((width /720.0) * 16));

        next_btn.setOnClickListener(v->{
            Intent intent = new Intent(SelectFontStyleActivity.this, LoadingActivity.class);
            startActivity(intent);
        });
    }



    class MyAdapter extends BaseAdapter {
        Context context;
        int layout, size;
        ArrayList<String> img;
        LayoutInflater inf;

        public MyAdapter(Context context, int layout, ArrayList<String> img, int size) {
            this.context = context;
            this.layout = layout;
            this.img = img;
            inf = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            this.size = size;
        }

        @Override
        public int getCount() {
            return img.size();
        }

        @Override
        public Object getItem(int position) {
            return img.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = inf.inflate(layout, parent,false);

            AbsListView.LayoutParams layoutParams= (AbsListView.LayoutParams) convertView.getLayoutParams();
            layoutParams.height = size;
            convertView.setLayoutParams(layoutParams);
            LinearLayout iv = convertView.findViewById(R.id.font_style);
            iv.setBackgroundResource(returnDrawable(img.get(position)));

            ImageView select_image = convertView.findViewById(R.id.select_image);


            iv.setOnClickListener(v->{

            });



            return convertView;
        }
    }
    public void listDrawable(){

        Field[] fields=R.drawable.class.getFields();

        for(int count=0; count < fields.length; count++){
            if (fields[count].getName().contains("font_style")) {
                Log.e("Drawable Asset: ", fields[count].getName());
                font_style_list.add(fields[count].getName());
            }
        }

    }

    public int returnDrawable(String file_name){
        return getResources().getIdentifier(file_name, "drawable", getPackageName());

    }


}
