package com.example.aircraftwar2024_220110120_wzf.DAO;

public class PlayerScore {
    private int rank;
    private String PlayerName;
    private int Score;
    private String PlayerTime;

    public PlayerScore(int rank, String PlayerName, int Score, String Playertime) {
        this.PlayerName = PlayerName;
        this.Score = Score;
        this.PlayerTime = Playertime;
        this.rank = rank;
    }

    /**
     * 获取
     * @return PlayerName
     */
    public String getPlayerName() {
        return PlayerName;
    }

    /**
     * 设置
     * @param PlayerName
     */
    public void setPlayerName(String PlayerName) {
        this.PlayerName = PlayerName;
    }

    /**
     * 获取
     * @return PlayerTime
     */
    public String getPlayerTime() {
        return PlayerTime;
    }

    /**
     * 设置
     * @param PlayerTime
     */
    public void setPlayerTime(String PlayerTime) {
        this.PlayerTime = PlayerTime;
    }

    /**
     * 获取
     * @return Score
     */
    public int getScore() {
        return Score;
    }

    /**
     * 设置
     * @param Score
     */
    public void setScore(int Score) {
        this.Score = Score;
    }

    /**
     * 获取
     * @return rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * 设置
     * @param rank
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    public String toString() {
        return  PlayerName + "," + Score + "," + PlayerTime;
    }
}
