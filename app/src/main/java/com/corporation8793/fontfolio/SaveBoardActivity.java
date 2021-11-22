package com.corporation8793.fontfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class SaveBoardActivity extends AppCompatActivity {
    RecyclerView save_board;
    RecyclerBoardAdapter mAdapter = null ;
    ArrayList<BoardItem> mList = new ArrayList<BoardItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_board);

        save_board = findViewById(R.id.save_board_list);

        mAdapter = new RecyclerBoardAdapter(mList);
        save_board.setAdapter(mAdapter);
    }
}