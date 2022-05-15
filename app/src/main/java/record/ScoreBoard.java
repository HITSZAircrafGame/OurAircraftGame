package record;

import java.io.*;
import java.util.*;

/**
 * 实现RecordDao的类*/
public class ScoreBoard implements RecordDao{

    private List<PlayerRecord> records;

    /**
     * 构造ScoreBoard的方法
     * 从文件中读取积分榜的数据
     * */
    public ScoreBoard(){
        records = new LinkedList<>();
        try{
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("src\\edu\\hitsz\\record\\scoreboard.txt"));
            records = (LinkedList<PlayerRecord>)input.readObject();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * ver4.0添加，将元素插入链表中
     * */
    @Override
    public void addRecord(int score, String time){
        String name = "Player" + (records.size() + 1);
        records.add(new PlayerRecord(score, name, time));
    }

    /**
     * ver4.0添加，若ScoreBoard对象被修改，将修改后的数据写入文件
     * */
    @Override
    public void updateRecords() {
        Collections.sort(records, new Comparator<PlayerRecord>(){
            @Override
            public int compare(PlayerRecord pr1, PlayerRecord pr2){
                return pr2.getScore() - pr1.getScore();
            }
        });
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("src\\edu\\hitsz\\record\\scoreboard.txt"));
            output.writeObject(records);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到保存的积分榜情况*/
    @Override
    public List<PlayerRecord> getAllRecords(){
        return records;
    }
}
