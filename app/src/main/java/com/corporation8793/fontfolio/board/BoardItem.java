package com.corporation8793.fontfolio.board;

import android.graphics.drawable.Drawable;

public class BoardItem {

    private String board_name;
    private Drawable board_image;

    private String font_name;



    public String getBoard_name() {
        return board_name;
    }

    public void setBoard_name(String board_name) {
        this.board_name = board_name;
    }

    public Drawable getBoard_image() {
        return board_image;
    }

    public void setBoard_image(Drawable board_image) {
        this.board_image = board_image;
    }

    public String getFont_name() {
        return font_name;
    }

    public void setFont_name(String font_name) {
        this.font_name = font_name;
    }



}
