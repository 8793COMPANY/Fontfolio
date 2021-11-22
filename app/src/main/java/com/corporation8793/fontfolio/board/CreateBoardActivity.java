package com.corporation8793.fontfolio.board;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.corporation8793.fontfolio.CustomView;
import com.corporation8793.fontfolio.R;

public class CreateBoardActivity extends AppCompatActivity {
    Button finish_btn, cancel_btn;
    EditText font_name_input_box, board_name_input_box;
    CustomView board_icon_view1,board_icon_view2,board_icon_view3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_board);

        board_icon_view1 = findViewById(R.id.board_icon_view1);
        board_icon_view2 = findViewById(R.id.board_icon_view2);
        board_icon_view3 = findViewById(R.id.board_icon_view3);

        font_name_input_box = findViewById(R.id.font_name_input_box);
        board_name_input_box = findViewById(R.id.board_name_input_box);

        finish_btn = findViewById(R.id.finish_btn);
        cancel_btn = findViewById(R.id.cancel_btn);


        board_icon_view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 선택된 별을 가져온다
                initSelectIcon();
                selectBoard(board_icon_view1);
            }
        });

        board_icon_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 선택된 별을 가져온다
                initSelectIcon();
                selectBoard(board_icon_view2);
            }
        });

        board_icon_view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 선택된 별을 가져온다
                initSelectIcon();
                selectBoard(board_icon_view3);
            }
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
                }else{
                    finish_btn.setBackgroundResource(R.drawable.action_bar_next_red_btn);
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
                }else{
                    finish_btn.setBackgroundResource(R.drawable.action_bar_next_red_btn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//
        finish_btn.setOnClickListener(v->{
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
}