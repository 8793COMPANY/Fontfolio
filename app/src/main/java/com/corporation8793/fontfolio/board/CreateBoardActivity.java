package com.corporation8793.fontfolio.board;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import com.corporation8793.fontfolio.CustomView;
import com.corporation8793.fontfolio.R;
import com.corporation8793.fontfolio.common.Fontfolio;
import com.corporation8793.fontfolio.library.room.entity.board.Board;
import com.google.android.datatransport.runtime.scheduling.DefaultScheduler;

import java.io.IOException;
import java.io.InputStream;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

public class CreateBoardActivity extends AppCompatActivity {
    Button finish_btn, cancel_btn;
    EditText font_name_input_box, board_name_input_box;
    CustomView board_icon_view1,board_icon_view2,board_icon_view3;

     Fontfolio fontfolio;

    private static final int REQUEST_CODE = 0;

    ImageView take_picture_view, get_picture_view;

    Switch secret_check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_board);

        board_icon_view1 = findViewById(R.id.board_icon_view1);
        board_icon_view2 = findViewById(R.id.board_icon_view2);
        board_icon_view3 = findViewById(R.id.board_icon_view3);

        font_name_input_box = findViewById(R.id.font_name_input_box);
        board_name_input_box = findViewById(R.id.board_name_input_box);

        secret_check = findViewById(R.id.secret_check);

        finish_btn = findViewById(R.id.finish_btn);
        cancel_btn = findViewById(R.id.cancel_btn);


        take_picture_view  = findViewById(R.id.take_picture_view);
        get_picture_view = findViewById(R.id.get_picture_view);

        take_picture_view.setClipToOutline(true);
        get_picture_view.setClipToOutline(true);

        fontfolio = new Fontfolio();
        fontfolio.getInstance(this);


        board_icon_view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 선택된 별을 가져온다
                initBoardImage();
                selectBoard(board_icon_view1);
            }
        });

        board_icon_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 선택된 별을 가져온다
                initBoardImage();
                selectBoard(board_icon_view2);
            }
        });

        board_icon_view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 선택된 별을 가져온다
                initBoardImage();
                selectBoard(board_icon_view3);
            }
        });

        take_picture_view.setOnClickListener(v->{
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i, 1);
        });

        get_picture_view.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, REQUEST_CODE);
        });

        font_name_input_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (board_name_input_box.getText().toString().trim().equals("") ||
                        font_name_input_box.getText().toString().trim().equals("")){
                    finish_btn.setBackgroundResource(R.drawable.action_bar_next_arrow_btn);
                    finish_btn.setEnabled(false);
                }else{
                    finish_btn.setBackgroundResource(R.drawable.action_bar_next_red_btn);
                    finish_btn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        board_name_input_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (board_name_input_box.getText().toString().trim().equals("") ||
                        font_name_input_box.getText().toString().trim().equals("")){
                    finish_btn.setBackgroundResource(R.drawable.action_bar_next_arrow_btn);
                    finish_btn.setEnabled(false);
                }else{
                    finish_btn.setBackgroundResource(R.drawable.action_bar_next_red_btn);
                    finish_btn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//
        finish_btn.setOnClickListener(v->{
            InsertRunnable insertRunnable = new InsertRunnable();
            Thread addThread = new Thread(insertRunnable);
            addThread.start();
            finish();
        });
        cancel_btn.setOnClickListener(v->{
            finish();
        });
    }



    public void initSelectIcon(){
        board_icon_view1.setSelected(false);
        board_icon_view2.setSelected(false);
        board_icon_view3.setSelected(false);
    }

    public void selectBoard(CustomView customView){
        boolean selected = customView.getSelected();
        if (selected == true) {
            selected = false; // 맨 오른쪽에 있을때는 처음으로 돌아간다.
        } else {
            selected = true;  // 1개씩 오른쪽으로 이동한다
        }
        Log.e("MainActivity", "selected=" + selected);
        // 다음 선택할 메소드를 실행
        customView.setSelected(selected); // 선택 상태를 변경한다.

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == REQUEST_CODE){
                try {
                    initBoardImage();
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    Drawable drawable = new BitmapDrawable(img);
                    get_picture_view.setBackground(drawable);



                }catch (Exception e){

                }
            }
            else if(requestCode == 1){
                initBoardImage();
                    Bundle extras = data.getExtras(); // Bitmap으로 컨버전
                    Bitmap imageBitmap = (Bitmap) extras.get("data");

//                    InputStream in = getContentResolver().openInputStream(data.getData());
//
//                    Bitmap img = BitmapFactory.decodeStream(in);
//                    in.close();

                    Drawable drawable = new BitmapDrawable(imageBitmap);
                    take_picture_view.setBackground(drawable);

            }
        }else if(resultCode == RESULT_CANCELED){

        }
    }

    public void initBoardImage(){
        take_picture_view.setBackgroundResource(R.drawable.board_image_camera_btn);
        get_picture_view.setBackgroundResource(R.drawable.board_image_gallery_btn);
        initSelectIcon();
    }

    class InsertRunnable implements Runnable {

        @Override
        public void run() {
            Log.e("secret_check",secret_check.isChecked()+"");
            Board board = new Board(fontfolio.db.boardDao().getAll().size(),font_name_input_box.getText().toString(), board_name_input_box.getText().toString(),secret_check.isChecked(),null);
            fontfolio.db.boardDao().insertAll(board);
        }
    }
}