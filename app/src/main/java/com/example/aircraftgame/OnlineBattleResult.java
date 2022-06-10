package com.example.aircraftgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.aircraftgame.NetGame.PlayerInfo;

public class OnlineBattleResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            int yourScore = PlayerInfo.playerInfo.getInt("Score");
            int otherScore = PlayerInfo.playerInfo.getInt("Score2");
            String otherName = PlayerInfo.playerInfo.getString("OpponentName");
            if (PlayerInfo.playerInfo.getInt("Score") > PlayerInfo.playerInfo.getInt("Score2")) {
                setContentView(R.layout.online_battle_win);
                setTextNeeded(true, yourScore, otherScore, otherName);
            } else {
                setContentView(R.layout.online_battle_lose);
                setTextNeeded(false, yourScore, otherScore, otherName);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    protected void setTextNeeded(boolean winFlag, int uScore, int oScore, String oName) {
        if (winFlag) {
            TextView yourScore = (TextView) findViewById(R.id.win_your_score);
            TextView otherScore = (TextView) findViewById(R.id.win_other_score);
            TextView otherName = (TextView) findViewById(R.id.win_other_name);
            yourScore.setText("你的得分：" + uScore);
            otherScore.setText("对手得分：" + oScore);
            otherName.setText("对手姓名：" + oName);
        } else {
            TextView yourScore = (TextView) findViewById(R.id.lose_your_score);
            TextView otherScore = (TextView) findViewById(R.id.lose_other_score);
            TextView otherName = (TextView) findViewById(R.id.lose_other_name);
            yourScore.setText("你的得分：" + uScore);
            otherScore.setText("对手得分：" + oScore);
            otherName.setText("对手姓名：" + oName);
        }
    }
}