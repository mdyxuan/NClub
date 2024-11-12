package com.example.bottom_main;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CategoryDetailActivity extends AppCompatActivity {
    private TextView categoryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);  // 設置正確的佈局

        // 通過 ID 初始化 TextView
        categoryTitle = findViewById(R.id.categoryTitle);

        // 獲取從 Intent 傳遞過來的分類名稱
        String categoryName = getIntent().getStringExtra("categoryName");

        // 如果分類名稱不為空，設置它
        if (categoryName != null) {
            categoryTitle.setText(categoryName);
        }
    }
}
