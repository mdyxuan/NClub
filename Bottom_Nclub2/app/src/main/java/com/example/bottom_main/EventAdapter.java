package com.example.bottom_main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private List<Event> recommendedEvents;  // 儲存推薦的活動列表
    private Context context;  // 上下文，方便開啟新的頁面

    // 建構子，初始化推薦活動列表和上下文
    public EventAdapter(List<Event> recommendedEvents, Context context) {
        this.recommendedEvents = recommendedEvents;
        this.context = context;
    }

    // 定義 ViewHolder 類別，用來綁定每一個項目的視圖
    public static class EventViewHolder extends RecyclerView.ViewHolder {
        ImageView eventImage;  // 活動圖片
        TextView eventTitle;  // 活動標題


        // ViewHolder 的建構子，綁定 XML 佈局中的視圖元件
        public EventViewHolder(View itemView) {
            super(itemView);
            eventImage = itemView.findViewById(R.id.eventImage);  // 綁定活動圖片
            eventTitle = itemView.findViewById(R.id.eventTitle);  // 綁定活動標題

        }
    }

    // 創建 ViewHolder，並將佈局檔案 item_event 轉換為視圖
    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    // 綁定每個項目數據到相應的視圖
    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = recommendedEvents.get(position);  // 取得當前活動
        holder.eventTitle.setText(event.getTitle());  // 設定活動標題

        // 使用 Glide 加載活動圖片
        Glide.with(holder.eventImage.getContext())
                .load(event.getImageUrl())  // 設定圖片網址
                .into(holder.eventImage);  // 加載圖片到 ImageView

        // 設置點擊事件，點擊活動項目時跳轉到活動詳情頁
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("eventId", event.getId());  // 傳遞活動 ID 到詳細頁面
            context.startActivity(intent);  // 啟動活動詳情頁面
        });


    }

    // 返回 RecyclerView 中項目的總數
    @Override
    public int getItemCount() {
        return recommendedEvents.size();
    }
}
