package com.example.bottom_main;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.bottom_main.Domain.ItemDomain;
import com.example.bottom_main.databinding.ActivityDetailBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailActivity extends BaseActivity {
    ActivityDetailBinding binding;
    private ItemDomain object;
    private String eventId;
    private boolean isFollowed = false; // 用於追蹤當前是否已關注

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 從 Intent 中獲取 eventId，用於加載活動詳情
        eventId = getIntent().getStringExtra("eventId");
        if (eventId != null) {
            loadEventDetails();
            checkFollowStatus(); // 檢查當前的關注狀態
        } else {
            setVariable();
        }

        // 設置關注按鈕點擊事件，切換關注狀態
        binding.follow.setOnClickListener(v -> toggleFollowStatus());
    }

    // 檢查關注狀態，從 Firebase 中獲取數據並更新 isFollowed 的值
    private void checkFollowStatus() {
        DatabaseReference followRef = FirebaseDatabase.getInstance().getReference("Follows").child(eventId);
        followRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                isFollowed = dataSnapshot.exists(); // 如果資料存在則表示已關注
                updateFollowIcon(); // 更新關注按鈕圖標
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DetailActivity.this, "無法加載關注狀態。", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 切換關注狀態並更新 Firebase
    private void toggleFollowStatus() {
        DatabaseReference followRef = FirebaseDatabase.getInstance().getReference("Follows").child(eventId);
        if (isFollowed) {
            // 若已關注則移除
            followRef.removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    isFollowed = false;
                    updateFollowIcon(); // 更新圖標顯示
                    Toast.makeText(DetailActivity.this, "取消關注", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // 若未關注則新增記錄
            followRef.setValue(true).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    isFollowed = true;
                    updateFollowIcon();
                    Toast.makeText(DetailActivity.this, "已關注", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // 更新關注圖標：已關注顯示 follow_icon，未關注顯示 fav_icon
    private void updateFollowIcon() {
        if (isFollowed) {
            binding.follow.setImageResource(R.drawable.follow_icon);
        } else {
            binding.follow.setImageResource(R.drawable.fav_icon);
        }
    }

    // 從 Firebase 加載活動詳細資訊
    private void loadEventDetails() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Items").child(eventId);
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    object = dataSnapshot.getValue(ItemDomain.class);
                    if (object != null) {
                        setVariable(); // 更新頁面顯示內容
                    }
                } else {
                    Toast.makeText(DetailActivity.this, "找不到活動詳細內容", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DetailActivity.this, "資料加載失敗：" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 設置活動詳細資訊到畫面
    private void setVariable() {
        if (object == null) return;
        binding.titleTxt.setText(object.getTitle());
        binding.priceTxt.setText("" + object.getPrice());
        binding.backBtn.setOnClickListener(view -> finish());
        binding.bedTxt.setText("" + object.getBed());
        binding.durationTxt.setText(object.getDuration());
        binding.distanceTxt.setText(object.getDistance());
        binding.descriptionTxt.setText(object.getDescription());
        binding.addressTxt.setText(object.getAddress());
        binding.ratingTxt.setText(object.getScore() + " 評分");
        binding.ratingBar.setRating((float) object.getScore());

        Glide.with(DetailActivity.this)
                .load(object.getPic())
                .into(binding.pic);

        binding.addToCartBtn.setOnClickListener(view -> {
            // 處理加入購物車邏輯
        });
    }
}
