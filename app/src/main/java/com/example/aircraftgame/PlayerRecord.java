package com.example.aircraftgame;

import org.litepal.crud.LitePalSupport;

public class PlayerRecord extends LitePalSupport{
    private String name;
    private int score;
    private String time;
    private String difficulty;

    public PlayerRecord(String name, int score, String time, String difficulty){
        this.name = name;
        this.score = score;
        this.time = time;
        this.difficulty = difficulty;
    }

    public int getScore(){
        return this.score;
    }

    public String getName(){
        return this.name;
    }

    public String getTime(){
        return this.time;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setScore(int score){
        this.score = score;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setTime(String time){
        this.time = time;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
