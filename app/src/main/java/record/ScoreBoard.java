package record;

import android.content.ContentValues;
import android.util.Log;
import android.widget.Toast;

import com.example.aircraftgame.PlayerRecord;

import org.litepal.LitePal;

import java.util.*;

/**
 * 实现RecordDao的类
 * */
public class ScoreBoard implements RecordDao{

    private List<PlayerRecord> records;
    private String difficulty;

    /**
     * 及时地更新DAO中的数据用以更新排行榜UI界面
     */
    public void realtimeUpdate() {
//        records = LitePal.order("score desc").find(PlayerRecord.class);
        records = LitePal.where("difficulty=?",difficulty).order("score desc").find(PlayerRecord.class);
    }

    /**
     * 构造ScoreBoard的方法
     * 从文件中读取积分榜的数据
     * */
    public ScoreBoard(String difficulty){
        records = new LinkedList<>();
        this.difficulty = difficulty;
        if(LitePal.getDatabase() != null) {
//            records = LitePal.order("score desc").find(PlayerRecord.class);
            records = LitePal.where("difficulty=?",difficulty).order("score desc").find(PlayerRecord.class);
        }
    }

    /**
     * ver4.0添加，将元素插入链表中
     * */
    @Override
    public void addRecord(String name, int score, String time, String difficulty){
        PlayerRecord pr = new PlayerRecord(name, score, time, difficulty);
        if(pr.save()) {
            Log.d("Save Data", "succeed!");
//            records = LitePal.order("score desc").find(PlayerRecord.class);
            records = LitePal.where("difficulty=?",difficulty).order("score desc").find(PlayerRecord.class);
        } else {
            Log.d("Save Data", "fail!");
        }

    }

    /**
     * 若用户取得了更好的分数，则更新其最佳分数和排名
     * */
    @Override
    public void updateRecords(String name,int score) {
        ContentValues cv = new ContentValues();
        cv.put("score", score);
        LitePal.updateAll(PlayerRecord.class, cv, "name=? and difficulty=?", name, difficulty);
    }

    /**
     * 删掉对应行玩家的游玩记录（仅限本地排行榜进行）
     *
     */
    @Override
    public void deleteRecords(String time){
        LitePal.deleteAll(PlayerRecord.class,"time=?",time);
//        records = LitePal.order("score desc").find(PlayerRecord.class);
        records = LitePal.where("difficulty=?",difficulty).order("score desc").find(PlayerRecord.class);
    }

    /**
     * 得到保存的积分榜情况*/
    @Override
    public List<PlayerRecord> getAllRecords(){
        return records;
    }
}
