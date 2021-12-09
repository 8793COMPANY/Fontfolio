package com.corporation8793.fontfolio.analysis;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.corporation8793.fontfolio.R;
import com.corporation8793.fontfolio.common.Fontfolio;

import java.util.ArrayList;

public class FontAnalysisAdapter extends RecyclerView.Adapter<FontAnalysisAdapter.ViewHolder> {

    private ArrayList<AnalysisItem> mData = null ;
    String msg = "";

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView font_name ;
        TextView percent ;
        TextView content;
        TextView copyright;
        Button like_font_btn;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            font_name = itemView.findViewById(R.id.fontName) ;
            percent = itemView.findViewById(R.id.percent);
            content = itemView.findViewById(R.id.content);
            copyright = itemView.findViewById(R.id.copyright);

            like_font_btn = itemView.findViewById(R.id.like_font_btn);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public FontAnalysisAdapter(ArrayList<AnalysisItem> list,String msg) {
        mData = list ;
        this.msg = msg;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public FontAnalysisAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.font_analysis_layout, parent, false) ;
        FontAnalysisAdapter.ViewHolder vh = new FontAnalysisAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(FontAnalysisAdapter.ViewHolder holder, int position) {
        holder.font_name.setText(mData.get(position).name) ;
        holder.percent.setText(mData.get(position).percent+"%");
        holder.copyright.setText(mData.get(position).copyright);

        if (msg.trim().equals("")){
            holder.content.setText("agent orange");
        }else{
            holder.content.setText(msg);
        }

//        holder.like_font_btn.setOnClickListener(v->{
//            if (holder.like_font_btn.isSelected()){
//                holder.like_font_btn.setBackgroundResource(R.drawable.font_info_heart_off);
//            }else{
//                holder.like_font_btn.setBackgroundResource(R.drawable.font_info);
//            }
//        });


        int check = (int) Fontfolio.list.parallelStream().filter(p->p.getFontName().equals(mData.get(position).name)).count();
        if (check == 1){
            holder.copyright.setText(Fontfolio.list.parallelStream().filter(p->p.getFontName().equals(mData.get(position).name)).findAny().get().getFontCopyrightHolder());
        }else{
            holder.copyright.setText("None");
        }

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }


}