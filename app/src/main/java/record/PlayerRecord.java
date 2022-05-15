package record;

import java.io.Serializable;

public class PlayerRecord implements Serializable{
    private int score;
    private String name;
    private String time;

    public PlayerRecord(int score, String name, String time){
        this.score = score;
        this.name = name;
        this.time = time;
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

    public void setScore(int score){
        this.score = score;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setTime(String time){
        this.time = time;
    }

    @Override
    public String toString(){
        return this.name + " \t " + this.score + " \t " + this.time;
    }
}
