package com.corporation8793.fontfolio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.w3c.dom.Text;

public class InitPwBottomDialog extends BottomSheetDialogFragment {
    String email;
    public InitPwBottomDialog(String email){
        this.email = email;

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.init_pw_sheet_layout,container,false);
        TextView sent_email_content = view.findViewById(R.id.sent_email_content);

        sent_email_content.getText().toString().replace("@",email);
        return view;
    }
}
