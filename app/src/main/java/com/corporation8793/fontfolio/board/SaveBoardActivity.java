package com.corporation8793.fontfolio.board;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
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

        InsertRunnable insertRunnable = new InsertRunnable();
        Thread addThread = new Thread(insertRunnable);
        addThread.start();

        mAdapter = new RecyclerBoardAdapter(getApplicationContext(),mList);
        save_board.setAdapter(mAdapter);



        save_board.setLayoutManager(new LinearLayoutManager(this)) ;

        RecyclerDecoration spaceDecoration = new RecyclerDecoration(15);
        save_board.addItemDecoration(spaceDecoration);


        create_btn.setOnClickListener(v->{
            Intent intent = new Intent(SaveBoardActivity.this, CreateBoardActivity.class);
            intent.putExtra("fontName","none");
            startActivity(intent);

        });

        cancel_btn.setOnClickListener(v->{
            finish();
        });

        mAdapter.notifyDataSetChanged();
    }


    public void addItem(String font, Drawable icon, String title) {
        Log.e("addItem","addItem");
        BoardItem item = new BoardItem();

        item.setFont_name(font);
        item.setBoard_image(icon);
        item.setBoard_name(title);

        mList.add(item);
    }

    public Bitmap byteArrayToBitmap(byte [] b) {
        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        return bmp;
    }
    class InsertRunnable implements Runnable {

        @Override
        public void run() {
            addItem("Abel-Regular" ,getDrawable(R.drawable.board_image_heart_icon),"Apple Design FOnt");
            addItem("Abel-Regular" ,getDrawable(R.drawable.board_image_heart_icon),"Favorites Font");
            addItem("Abel-Regular" ,getDrawable(R.drawable.board_image_heart_icon),"Free commercial font");
            addItem("Abel-Regular" ,getDrawable(R.drawable.board_image_heart_icon),"My Design Project Font");

            int size = fontfolio.db.boardDao().getAll().size();
            Log.e("size",size+"");
            for (int i=0; i<size; i++){
                Drawable drawable = new BitmapDrawable(byteArrayToBitmap(fontfolio.db.boardDao().getAll().get(i).getImage()));
                addItem(fontfolio.db.boardDao().getAll().get(i).getFontName(),
                        drawable,
                        fontfolio.db.boardDao().getAll().get(i).getBoardName());
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                }
            });

        }

    }


    @Override
    protected void onResume() {
        super.onResume();
//        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("restart in","???????");
            mList.clear();


            InsertRunnable insertRunnable = new InsertRunnable();
            Thread addThread = new Thread(insertRunnable);
            addThread.start();

//            mAdapter.notifyDataSetChanged();
    }


}