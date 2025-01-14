package com.example.aircraftwar2024_220110120_wzf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024_220110120_wzf.game.BaseGame;
import com.example.aircraftwar2024_220110120_wzf.game.EasyGame;
import com.example.aircraftwar2024_220110120_wzf.game.HardGame;
import com.example.aircraftwar2024_220110120_wzf.game.MediumGame;



public class GameActivity extends AppCompatActivity {
    private static final String TAG = "GameActivity";

    private int gameType=0;
    private String musicPreference;
    public static int screenWidth,screenHeight;
    private boolean showEnemyScore = false;

    public String difficultySelection;

    // 定义主线程 Handler
    public Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 处理接收到的消息
            if(msg.what == 10) {
                Intent intent = new Intent(GameActivity.this, SimpleAdapter.class);
                intent.putExtra("difficultySelection",difficultySelection);
                intent.putExtra("Score",msg.arg1);
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().addActivity(this);

        getScreenHW();
        if(getIntent() != null){
            gameType = getIntent().getIntExtra("gameType",1);
            musicPreference = getIntent().getStringExtra("music");
            showEnemyScore = getIntent().getBooleanExtra("showEnemyScore",false);
        }

        BaseGame baseGameView = null;
        switch (gameType) {
            case 1:
                baseGameView = new EasyGame(this, mHandler,musicPreference,showEnemyScore);
                difficultySelection = "easy";
                break;
            case 2:
                baseGameView = new MediumGame(this, mHandler,musicPreference,showEnemyScore);
                difficultySelection = "medium";
                break;
            case 3:
                baseGameView = new HardGame(this, mHandler,musicPreference,showEnemyScore);
                difficultySelection = "hard";
                break;
        }
        setContentView(baseGameView);
    }

    public void getScreenHW(){
        //定义DisplayMetrics 对象
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getDisplay().getRealMetrics(dm);

        //窗口的宽度
        screenWidth= dm.widthPixels;
        //窗口高度
        screenHeight = dm.heightPixels;

        Log.i(TAG, "screenWidth : " + screenWidth + " screenHeight : " + screenHeight);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}