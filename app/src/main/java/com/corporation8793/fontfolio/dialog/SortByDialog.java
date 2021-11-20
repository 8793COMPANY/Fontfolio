package com.corporation8793.fontfolio.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
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

import com.corporation8793.fontfolio.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class SortByDialog extends BottomSheetDialogFragment {
    LinearLayout close_btn;
    TextView sort_by_recommended,sort_by_random,sort_by_popularity;
    Button recommended_check,random_check,popularity_check;
    public SortByDialog(){

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sort_by_sheet_layout,container,false);
        close_btn = view.findViewById(R.id.close_btn);

        sort_by_recommended = view.findViewById(R.id.sort_by_recommended);
        sort_by_random = view.findViewById(R.id.sort_by_random);
        sort_by_popularity = view.findViewById(R.id.sort_by_popularity);

        recommended_check = view.findViewById(R.id.recommeded_check);
        random_check = view.findViewById(R.id.random_check);
        popularity_check = view.findViewById(R.id.popularity_check);

        close_btn.setOnClickListener(v->{
            Log.e("clici","!");
            dismiss();
        });

        check_init();
        recommended_check.setVisibility(View.VISIBLE);

        sort_by_recommended.setOnClickListener(v->{
            check_init();
            recommended_check.setVisibility(View.VISIBLE);
        });

        sort_by_random.setOnClickListener(v->{
            check_init();
            random_check.setVisibility(View.VISIBLE);
        });

        sort_by_popularity.setOnClickListener(v->{
            check_init();
            popularity_check.setVisibility(View.VISIBLE);
        });




        return view;
    }

    public void check_init(){
        recommended_check.setVisibility(View.INVISIBLE);
        random_check.setVisibility(View.INVISIBLE);
        popularity_check.setVisibility(View.INVISIBLE);
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
}
