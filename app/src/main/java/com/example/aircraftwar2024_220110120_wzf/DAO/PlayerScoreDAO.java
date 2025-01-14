package com.example.aircraftwar2024_220110120_wzf.DAO;

import java.util.List;

public interface PlayerScoreDAO {
    void findPlayerScoreByName(String PlayerName);
    List<PlayerScore> getAllPlayerScores();
    void doAdd(PlayerScore playerScore);
    void readFromFile(String filePath);
    void writeToFile(String filePath);
}
