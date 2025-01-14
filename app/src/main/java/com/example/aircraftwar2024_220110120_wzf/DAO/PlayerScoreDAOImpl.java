package com.example.aircraftwar2024_220110120_wzf.DAO;

import android.content.Context;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

public class PlayerScoreDAOImpl implements PlayerScoreDAO{
    private List<PlayerScore> playerScores;
    private Context context;

    public PlayerScoreDAOImpl(Context context){
        playerScores = new ArrayList<>();
        this.context = context;
    }

    @Override
    public void readFromFile(String filePath) {
        try {
            FileInputStream fis = context.openFileInput(filePath);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    int rank = Integer.parseInt(data[0].trim());
                    String playerName = data[1].trim();
                    int score = Integer.parseInt(data[2].trim());
                    String playerTime = data[3].trim();
                    PlayerScore playerScore = new PlayerScore(rank, playerName, score, playerTime);
                    playerScores.add(playerScore);
                } else {
                    System.err.println("Invalid data format in file: " + line);
                }
            }

            br.close();
        } catch (FileNotFoundException e) {
            // 文件不存在，创建一个新文件
            createNewFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createNewFile(String filePath) {
        try {
            FileOutputStream fos = context.openFileOutput(filePath, context.MODE_PRIVATE);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void findPlayerScoreByName(String PlayerName) {
        for(PlayerScore item:playerScores){
            if(item.getPlayerName().equals(PlayerName)){
                System.out.println("Find Player:Rank [" + item.getRank()+"]"+"ID [" + PlayerName + "]," +
                                    "Player Score [" + item.getScore() + "],"+
                                    "Player Time [" + item.getPlayerTime() + "]");
            }
        }
    }

    @Override
    public List<PlayerScore> getAllPlayerScores() {
        return playerScores;
    }

    @Override
    public void doAdd(PlayerScore playerScore) {
        playerScores.add(playerScore);
        System.out.println("Add new PlayerScore: ID [" + playerScore.getPlayerName() + "]," +
                            " Player Score [" + playerScore.getScore() + "], Player Time ["+ playerScore.getPlayerTime()+"]");
    }

    @Override
    public void writeToFile(String fileName) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            for (PlayerScore playerScore : playerScores) {
                bw.write(playerScore.getRank() + "," + playerScore.getPlayerName() + "," + playerScore.getScore() + "," + playerScore.getPlayerTime());
                bw.newLine(); // 写入新行
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sortPlayerScoreAndSetRank() {
        // 根据分数从高到低排序
        playerScores.sort(Comparator.comparingInt(PlayerScore::getScore).reversed());
        for (int i = 0; i < playerScores.size(); i++) {
            playerScores.get(i).setRank(i + 1); // 设置排名，从1开始
        }
    }

    public String[][] ReadScoreRecord(String fileName) {
        List<String[]> dataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                dataList.add(parts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList.toArray(new String[0][]);
    }

    public void updatePlayerScores(List<PlayerScore> updatedScores){
        this.playerScores = updatedScores;
    }

}
