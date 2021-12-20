package com.corporation8793.fontfolio.board;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.corporation8793.fontfolio.R;
import com.corporation8793.fontfolio.common.Fontfolio;
import com.corporation8793.fontfolio.library.room.entity.board.Board;

import java.util.ArrayList;

public class SaveBoardActivity extends AppCompatActivity {
    RecyclerView save_board;
    RecyclerBoardAdapter mAdapter = null ;
    ArrayList<BoardItem> mList = new ArrayList<BoardItem>();
    Button create_btn, cancel_btn;

    Fontfolio fontfolio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_board);

        fontfolio = new Fontfolio();
        fontfolio.getInstance(this);

        save_board = findViewById(R.id.save_board_list);
        create_btn = findViewById(R.id.create_btn);
        cancel_btn = findViewById(R.id.cancel_btn);

        mAdapter = new RecyclerBoardAdapter(mList);
        save_board.setAdapter(mAdapter);

        InsertRunnable insertRunnable = new InsertRunnable();
        Thread addThread = new Thread(insertRunnable);
        addThread.start();

        save_board.setLayoutManager(new LinearLayoutManager(this)) ;

        RecyclerDecoration spaceDecoration = new RecyclerDecoration(15);
        save_board.addItemDecoration(spaceDecoration);

        addItem(R.drawable.board_image_heart_icon,"Apple Design FOnt");
        addItem(R.drawable.board_image_heart_icon,"Favorites Font");
        addItem(R.drawable.board_image_heart_icon,"Free commercial font");
        addItem(R.drawable.board_image_heart_icon,"My Design Project Font");

        mAdapter.notifyDataSetChanged();


        create_btn.setOnClickListener(v->{
            Intent intent = new Intent(SaveBoardActivity.this, CreateBoardActivity.class);
            startActivity(intent);

        });

        cancel_btn.setOnClickListener(v->{
            finish();
        });
    }


    public void addItem(int icon, String title) {
        BoardItem item = new BoardItem();

        item.setBoard_image(icon);
        item.setBoard_name(title);

        mList.add(item);
    }

    class InsertRunnable implements Runnable {

        @Override
        public void run() {
            int size = fontfolio.db.boardDao().getAll().size();
            for (int i=0; i<size; i++){
                addItem(R.drawable.board_image_heart_icon,fontfolio.db.boardDao().getAll().get(i).getFontName().toString());
            }
//            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("restart in","???????");
        if (mAdapter != null){
            mList.clear();
            addItem(R.drawable.board_image_heart_icon,"Apple Design FOnt");
            addItem(R.drawable.board_image_heart_icon,"Favorites Font");
            addItem(R.drawable.board_image_heart_icon,"Free commercial font");
            addItem(R.drawable.board_image_heart_icon,"My Design Project Font");
            
            InsertRunnable insertRunnable = new InsertRunnable();
            Thread addThread = new Thread(insertRunnable);
            addThread.start();
            mAdapter.notifyDataSetChanged();
        }
    }
}