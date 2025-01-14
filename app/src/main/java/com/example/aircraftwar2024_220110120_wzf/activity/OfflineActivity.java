package com.example.aircraftwar2024_220110120_wzf.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aircraftwar2024_220110120_wzf.R;

public class OfflineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        ActivityManager.getActivityManager().addActivity(this);

        Intent intent = getIntent();
        String musicPreference = intent.getStringExtra("music");
        Toast.makeText(this, "Music preference: " + musicPreference, Toast.LENGTH_SHORT).show();


        // 获取按钮的引用
        Button buttonEasy = findViewById(R.id.button_easy);
        Button buttonMedium = findViewById(R.id.button_medium);
        Button buttonHard = findViewById(R.id.button_hard);

        // 设置按钮点击监听器
        buttonEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OfflineActivity.this, "Easy mode selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OfflineActivity.this, GameActivity.class);
                intent.putExtra("music",musicPreference);
                intent.putExtra("gameType",1);
                startActivity(intent);
            }
        });

        buttonMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OfflineActivity.this, "Medium mode selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OfflineActivity.this, GameActivity.class);
                intent.putExtra("music",musicPreference);
                intent.putExtra("gameType",2);
                startActivity(intent);
            }
        });

        buttonHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OfflineActivity.this, "Hard mode selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OfflineActivity.this, GameActivity.class);
                intent.putExtra("music",musicPreference);
                intent.putExtra("gameType",3);
                startActivity(intent);
            }
        });

    }
}