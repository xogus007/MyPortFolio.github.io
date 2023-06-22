package com.lkw.searchbar.login.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private static List<String> data; // 데이터 리스트 (예시로 String 리스트 사용)
    private static OnItemClickListener clickListener;

    public MyAdapter(List<String> data) {
        MyAdapter.data = data;
    }

    // 뷰 홀더 클래스
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    String item = data.get(position);
                    clickListener.onItemClick(item);
                }
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        clickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 뷰 홀더를 생성하고 뷰를 연결합니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 뷰 홀더에 데이터를 설정합니다.
        String item = data.get(position);
        holder.textView.setText(item);
        holder.textView.setTextSize(12); // 작은 텍스트 사이즈로 설정
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
