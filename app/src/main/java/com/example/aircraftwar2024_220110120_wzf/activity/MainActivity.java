package com.example.aircraftwar2024_220110120_wzf.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.aircraftwar2024_220110120_wzf.R;
import com.example.aircraftwar2024_220110120_wzf.game.BaseGame;
import com.example.aircraftwar2024_220110120_wzf.game.MediumGame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class MainActivity extends AppCompatActivity {

    private Socket socket;
    private PrintWriter writer;
    private Handler handler;
    private String musicPreference;
    public static int screenWidth,screenHeight;
    private static final String TAG = "MainActivity";
    private BaseGame baseGameView = null;
    private AlertDialog alertDialog;

    private int OwnScore = 0;
    private int EnemyScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityManager.getActivityManager().addActivity(this);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            ActivityManager.getActivityManager().finishActivity(GameActivity.class);
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == R.id.radioButton_enable_music) {
                musicPreference = "enable";
            } else if (selectedId == R.id.radioButton_disable_music) {
                musicPreference = "disable";
            } else {
                musicPreference = "unknown";
            }
            Intent intent = new Intent(MainActivity.this, OfflineActivity.class);
            intent.putExtra("music", musicPreference);
            startActivity(intent);
        });

        Button together_button = findViewById(R.id.together_button);
        together_button.setOnClickListener(v -> {
            ActivityManager.getActivityManager().finishActivity(GameActivity.class);
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == R.id.radioButton_enable_music) {
                musicPreference = "enable";
            } else if (selectedId == R.id.radioButton_disable_music) {
                musicPreference = "disable";
            } else {
                musicPreference = "unknown";
            }
            new Thread(new NetConn(handler)).start();
        });

        handler = new Handler(getMainLooper()) {
            //当数据处理子线程更新数据后发送消息给UI线程，UI线程更新UI
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1: // connecting
                        showProgressDialog("匹配中，请等待……");
                        break;
                    case 2: // StartGame
                        if (alertDialog != null && alertDialog.isShowing()) {
                            alertDialog.dismiss();
                        }
                        startGameActivity();
                        break;
                    case 3: // opponentScore
                        // 把分数传进游戏并显示
                        int opponentScore = msg.arg1;
                        baseGameView.updateEnemyScore(opponentScore);
                        break;
                    case 4: //YourScore                               0
                        int score = msg.arg1;
                        new Thread(() -> {
                            try {
                                Thread.sleep(1000); // 500毫秒的延迟
                                writer.println("Score:" + score);
                                Log.d("GameDebug","SentScore" + score);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }).start();
                        break;
                    case 10: // end
                        OwnScore = msg.arg1;
                        new Thread(){
                            @Override
                            public void run(){
                                writer.println("end:"+OwnScore);
                            }
                        }.start();
                        break;
                    case 11:
                        setContentView(R.layout.activity_result);

                        // 获取并设置自己的分数 TextView
                        TextView yourScoreTextView = findViewById(R.id.yourScoreTextView);
                        if (yourScoreTextView != null) {
                            yourScoreTextView.setText("你的分数：" + OwnScore);
                        } else {
                            // 处理 TextView 为空的情况
                            Log.e("ActivityResult", "yourScoreTextView is null");
                        }

                        // 获取并设置对手的分数 TextView
                        TextView opponentScoreTextView = findViewById(R.id.opponentScoreTextView);
                        if (opponentScoreTextView != null) {
                            opponentScoreTextView.setText("对手分数：" + EnemyScore);
                        } else {
                            // 处理 TextView 为空的情况
                            Log.e("ActivityResult", "opponentScoreTextView is null");
                        }
                        break;

                }
            }
        };
    }

    private void showProgressDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        alertDialog = builder.create();
        alertDialog.show();
    }
    private void startGameActivity(){
        getScreenHW();
        baseGameView = new MediumGame(this, handler, musicPreference, true);
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

    protected class NetConn extends Thread{
        private BufferedReader in;
        private Handler toClientHandler;
        public NetConn(Handler myHandler){
                this.toClientHandler = myHandler;
            }
            @Override
            public void run(){
                try{
                    socket = new Socket();

                    socket.connect(new InetSocketAddress
                            ("10.0.2.2",9999));
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
                    writer = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream(),"utf-8")),true);
                    writer.println("Match");

                    //接收服务器返回的数据
                    Thread receiveServerMsg =  new Thread(){
                        @Override
                        public void run(){
                            String fromserver = null;
                            try{
                                while((fromserver = in.readLine())!=null)
                                {
                                    //发送消息给UI线程
                                    if(fromserver.equals("connecting")){
                                        Message msg = new Message();
                                        msg.what = 1;
                                        toClientHandler.sendMessage(msg);
                                    } else if (fromserver.equals("StartGame")) {
                                        Message msg = new Message();
                                        msg.what = 2;
                                        toClientHandler.sendMessage(msg);
                                    } else if (fromserver.startsWith("opponentPlayer:")) {
                                        int opponentScore = Integer.parseInt(fromserver.substring("opponentPlayer:".length()).trim());
                                        Message msg = new Message();
                                        msg.what = 3;
                                        msg.arg1 = opponentScore;
                                        toClientHandler.sendMessage(msg);
                                    } else if(fromserver.equals("EndGame")) {
                                        Message msg = new Message();
                                        msg.what = 11;
                                        toClientHandler.sendMessage(msg);
                                    } else if(fromserver.startsWith("aEndGame")) {
                                        if(EnemyScore == 0)
                                            EnemyScore = Integer.parseInt(fromserver.substring(8).trim());
                                        Log.d("EndDebug", "EnemyScore" + EnemyScore);
                                    } else if(fromserver.startsWith("EndGame")) {
                                        Message msg = new Message();
                                        msg.what = 11;
                                        if(EnemyScore == 0)
                                            EnemyScore = Integer.parseInt(fromserver.substring(7).trim());
                                        toClientHandler.sendMessage(msg);
                                        Log.d("EndDebug", "EnemyScore" + EnemyScore);
                                    }
                                }
                            }catch (IOException ex){
                                ex.printStackTrace();
                            }
                        }
                    };
                    receiveServerMsg.start();
                }catch(UnknownHostException ex){
                    ex.printStackTrace();
                }catch(IOException ex){
                    ex.printStackTrace();
                }
        }
    }
}