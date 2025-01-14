package com.example.aircraftwar2024_220110120_wzf.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024_220110120_wzf.DAO.PlayerScore;
import com.example.aircraftwar2024_220110120_wzf.DAO.PlayerScoreDAOImpl;
import com.example.aircraftwar2024_220110120_wzf.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SimpleAdapter extends AppCompatActivity {

    private PlayerScoreDAOImpl playerScoreDAOImpl = new PlayerScoreDAOImpl(this);
    private String filename;
    private android.widget.SimpleAdapter listItemAdapter;
    private ArrayList<Map<String, Object>> listitem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ActivityManager.getActivityManager().addActivity(this);

        Intent intent = getIntent();
        String difficultySelection = intent.getStringExtra("difficultySelection");
        int Score = intent.getIntExtra("Score", 0);
        filename = difficultySelection + "PlayerScoreRecord";

        TextView difficultyTextView = findViewById(R.id.difficultyTextView);
        difficultyTextView.setText("难度：" + difficultySelection);

        playerScoreDAOImpl.readFromFile(filename);
        addData(Score);
        playerScoreDAOImpl.sortPlayerScoreAndSetRank();
        playerScoreDAOImpl.writeToFile(filename);

        //获得Layout里面的ListView
        ListView list = findViewById(R.id.ListView);
        //生成适配器的Item和动态数组对应的元素
        listItemAdapter = new android.widget.SimpleAdapter(
                this,
                getData(),
                R.layout.activity_item,
                new String[]{"rank", "name", "score", "time"},
                new int[]{R.id.rank, R.id.name, R.id.score, R.id.time});

        //添加并且显示
        list.setAdapter(listItemAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Map<String, Object> clkmap = (Map<String, Object>) arg0.getItemAtPosition(arg2);
                String text = "Rank [" + Objects.requireNonNull(clkmap.get("rank")) + "], ID [" + Objects.requireNonNull(clkmap.get("name")) + "], " +
                        "Score [" + Objects.requireNonNull(clkmap.get("score")) + "], Time [" + Objects.requireNonNull(clkmap.get("time")) + "]";
                Toast.makeText(SimpleAdapter.this, text, Toast.LENGTH_SHORT).show();
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SimpleAdapter.this);
                builder.setTitle("确认删除");
                builder.setMessage("确定要删除这条记录吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 删除列表中的项
                        Map<String, Object> item = (Map<String, Object>) parent.getItemAtPosition(position);
                        listitem.remove(position);
                        for (int i = 0; i < listitem.size(); i++) {
                            listitem.get(i).put("rank", i + 1);
                        }
                        // 更新playerScoreDAOImpl的数据
                        List<PlayerScore> updatedScores = new ArrayList<>();
                        for (Map<String, Object> map : listitem) {
                            int rank = (int) map.get("rank");
                            String name = (String) map.get("name");
                            int score = (int) map.get("score");
                            String time = (String) map.get("time");
                            updatedScores.add(new PlayerScore(rank, name, score, time));
                        }
                        playerScoreDAOImpl.updatePlayerScores(updatedScores);
                        playerScoreDAOImpl.writeToFile(filename);
                        listItemAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
                return true; // 返回true表示消费了长按事件，不会触发单击事件
            }
        });

        //实现按钮返回
        Button button = findViewById(R.id.backButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.getActivityManager().finishActivity(SimpleAdapter.class);
                ActivityManager.getActivityManager().finishActivity(GameActivity.class);
                ActivityManager.getActivityManager().finishActivity(OfflineActivity.class);
                Toast.makeText(SimpleAdapter.this, "Output file saved successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Map<String, Object>> getData() {

        listitem = new ArrayList<>();
        List<PlayerScore> playerScores = playerScoreDAOImpl.getAllPlayerScores();

        for (PlayerScore playerScore : playerScores) {
            Map<String, Object> map = new HashMap<>();
            map.put("rank", playerScore.getRank());
            map.put("name", playerScore.getPlayerName());
            map.put("score", playerScore.getScore());
            map.put("time", playerScore.getPlayerTime());
            listitem.add(map);
        }

        return listitem;
    }

    private void addData(int score) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");
        String formattedDateTime = now.format(formatter);
        PlayerScore playerScore = new PlayerScore(0, "test", score, formattedDateTime);
        playerScoreDAOImpl.doAdd(playerScore);
    }

}