package com.corporation8793.fontfolio.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.corporation8793.fontfolio.R;
import com.corporation8793.fontfolio.common.Fontfolio;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class SortByDialog extends BottomSheetDialogFragment {
    LinearLayout close_btn;
    TextView sort_by_ranking, sort_by_open_font, sort_by_paid_font, sort_by_all_font;
    Button ranking_check, open_font_check, paid_font_check, all_font_check;
    private DialogInterface.OnDismissListener onDismissListener = null;
    public SortByDialog(){

    }

    public interface DismissListener {
        void onDismiss(int hasDate);
    }

    public DismissListener listener = null;

    public void setDismissListener(DismissListener listener) {
        this.listener = listener;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sort_by_sheet_layout,container,false);
        close_btn = view.findViewById(R.id.close_btn);

        sort_by_ranking = view.findViewById(R.id.sort_by_recommended);
        sort_by_open_font = view.findViewById(R.id.sort_by_open_font);
        sort_by_paid_font = view.findViewById(R.id.sort_by_paid_font);
        sort_by_all_font = view.findViewById(R.id.sort_by_all_font);

        ranking_check = view.findViewById(R.id.recommeded_check);
        open_font_check = view.findViewById(R.id.random_check);
        paid_font_check = view.findViewById(R.id.popularity_check);
        all_font_check = view.findViewById(R.id.all_font_check);

        close_btn.setOnClickListener(v->{
            Log.e("clici","!");
            dismiss();
        });

        int sortby = Fontfolio.prefs.getInt("sortBy",1);
        if (sortby== 1){
            ranking_check.setVisibility(View.VISIBLE);
        }else if (sortby == 2){
            open_font_check.setVisibility(View.VISIBLE);
        }else if(sortby == 3){
            paid_font_check.setVisibility(View.VISIBLE);
        }else if (sortby == 4){
            all_font_check.setVisibility(View.VISIBLE);
        }

        sort_by_ranking.setOnClickListener(v->{
            check_init();
            ranking_check.setVisibility(View.VISIBLE);
            Fontfolio.prefs.setInt("sortBy",1);
        });

        sort_by_open_font.setOnClickListener(v->{
            check_init();
            open_font_check.setVisibility(View.VISIBLE);
            Fontfolio.prefs.setInt("sortBy",2);
        });

        sort_by_paid_font.setOnClickListener(v->{
            check_init();
            paid_font_check.setVisibility(View.VISIBLE);
            Fontfolio.prefs.setInt("sortBy",3);
        });

        sort_by_all_font.setOnClickListener(v->{
            check_init();
            all_font_check.setVisibility(View.VISIBLE);
            Fontfolio.prefs.setInt("sortBy",4);
        });




        return view;
    }

    public void check_init(){
        ranking_check.setVisibility(View.INVISIBLE);
        open_font_check.setVisibility(View.INVISIBLE);
        paid_font_check.setVisibility(View.INVISIBLE);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.e("in","!!");
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
            setupRatio(bottomSheetDialog);
        });
        return  dialog;
    }


    private void setupRatio(BottomSheetDialog bottomSheetDialog) {
        //id = com.google.android.material.R.id.design_bottom_sheet for Material Components
        //id = android.support.design.R.id.design_bottom_sheet for support librares
        FrameLayout bottomSheet = (FrameLayout)
                bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        layoutParams.height = getBottomSheetDialogDefaultHeight();
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
    private int getBottomSheetDialogDefaultHeight() {
        return (int) ((getWindowHeight() /1520.0) * 820);
    }
    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.e("hi","in dismisslistener");
        if (listener != null) {
            listener.onDismiss(Fontfolio.prefs.getInt("sortBy",4));
        }
    }
}
