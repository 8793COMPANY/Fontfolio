package com.corporation8793.fontfolio;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.corporation8793.fontfolio.common.Fontfolio;
import com.corporation8793.fontfolio.login.AppLoginActivity;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class SelectFontStyleActivity extends AppCompatActivity {

    ArrayList<MyFontStyle> font_style_list = new ArrayList();
    ArrayList<Integer> select_list = new ArrayList<>();

    LinearLayout next_btn;
    TextView next_btn_text;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_font_style);

        next_btn = findViewById(R.id.next_btn);
        next_btn_text = findViewById(R.id.next_btn_text);

        next_btn.setEnabled(false);
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
        gv.setVerticalSpacing((int)((width /720.0) * 16));

        next_btn.setOnClickListener(v->{
            String fontStyle = "";
            for (int i =0; i<select_list.size(); i++){
                fontStyle += font_style_list.get(i).fontName+"/";
            }
            Log.e("fontStyle",fontStyle.substring(0,fontStyle.length()-1));
            Fontfolio.prefs.setString("font_style",fontStyle.substring(0,fontStyle.length()-1));
            Intent intent = new Intent(SelectFontStyleActivity.this, LoadingActivity.class);
            startActivity(intent);
            finish();
        });


        gv.setOnItemClickListener
                (new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        adapter.changeImage(position);
                        if (select_list.contains(position)){
                            select_list.remove(position);
                        }else{
                            select_list.add(position);
                        }

                        Log.e("font Name",font_style_list.get(position).fontName);
                        Log.e("count",adapter.checkImageChange()+"");

                        if (adapter.checkImageChange() >3){
                            next_btn.setBackgroundTintList((ColorStateList.valueOf(Color.parseColor("#dd0000"))));
                            next_btn_text.setTextColor(getColor(R.color.white));
                            next_btn.setEnabled(true);
                        }else{
                            next_btn.setBackgroundTintList((ColorStateList.valueOf(Color.parseColor("#0D000000"))));
                            next_btn_text.setTextColor(Color.parseColor("#80000000"));
                            next_btn.setEnabled(false);
                        }
                    }
                });
    }



    class MyAdapter extends BaseAdapter {
        Context context;
        int layout, size;
        ArrayList<MyFontStyle>  img;
        LayoutInflater inf;

        public MyAdapter(Context context, int layout, ArrayList<MyFontStyle> img, int size) {
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
            iv.setBackgroundResource(returnDrawable(img.get(position).fontName));


            LinearLayout font_style = convertView.findViewById(R.id.font_style);
            ImageView select_image = convertView.findViewById(R.id.select_image);

            if(img.get(position).isImageChanged()){
                select_image.setVisibility(View.VISIBLE);
            }else{
                select_image.setVisibility(View.INVISIBLE);
            }



            return convertView;
        }

        public void changeImage(int index) {
            if (img.get(index).isImageChanged()){
                img.get(index).setImageChanged(false);
            }else{
                img.get(index).setImageChanged(true);
            }

            notifyDataSetChanged();

        }

        public int checkImageChange() {
           int count = 0;
            for(int i =0; i < img.size(); i++){
               if (img.get(i).isImageChanged){
                   count++;
               }
           }

            return count;

        }
    }


    public void listDrawable(){
        Field[]  fields = R.drawable.class.getFields();

        for(int count=0; count < fields.length; count++){
            if (fields[count].getName().contains("font_style") && !fields[count].getName().equals("font_style_select_img")) {
                MyFontStyle fontStyle = new MyFontStyle();
                fontStyle.fontName = fields[count].getName();
                fontStyle.isImageChanged = false;
                Log.e("Drawable Asset: ", fields[count].getName());
                font_style_list.add(fontStyle);
            }
        }

    }

    public int returnDrawable(String file_name){
        return getResources().getIdentifier(file_name, "drawable", getPackageName());

    }

    class MyFontStyle{
        public boolean isImageChanged;
        public String fontName;

        public String getFontName() {
            return fontName;
        }

        public void setFontName(String fontName) {
            this.fontName = fontName;
        }


        public boolean isImageChanged() {
            return isImageChanged;
        }

        public void setImageChanged(boolean imageChanged) {
            isImageChanged = imageChanged;
        }
    }


}
