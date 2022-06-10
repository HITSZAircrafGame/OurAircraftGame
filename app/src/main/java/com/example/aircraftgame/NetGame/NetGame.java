package com.example.aircraftgame.NetGame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.Log;

import com.example.aircraftgame.GameDifficulty.DifficultGameView;
import com.example.aircraftgame.R;
import com.example.aircraftgame.RankBoard;

import PublicLockAndFlag.GameOverFlag;
import PublicLockAndFlag.OnlineGameOver;

//Online Playing Model
@SuppressLint("ViewConstructor")
public class NetGame extends DifficultGameView {
    public String TAG="NetGame";

    //另一个玩家的分数
    public int score2;
    public NetGame(Context context, int screenWidth, int screenHeight) {
        super(context, screenWidth, screenHeight);
        score2=0;
        OnlineGameOver.flag=false;
        Log.i(TAG,"网络游戏进行中");
        PlayerInfo.init();
    }

    @SuppressLint("ResourceAsColor")
    protected void paintScoreAndLife(Canvas cvs) {
        //更新得分
        //对手
            try{
                PlayerInfo.playerInfo.put("Score",score);
                score2=PlayerInfo.playerInfo.getInt("Score2");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        int x = 10;
        int y = 100;
        mPaint.setColor(R.color.red);
        mPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD));
        mPaint.setTextSize(screenHeight/25);
        cvs.drawText("SCORE:" + this.score, x, y, mPaint);
        y = y + 100;
        cvs.drawText("LIFE:" + this.heroAircraft.getHp(), x, y, mPaint);

        y=y+100;
        mPaint.setColor(R.color.red);
        cvs.drawText("opponent Player",x,y,mPaint);
        y+=100;
        cvs.drawText("SCORE:"+score2,x,y,mPaint);
    }

    //重写游戏结束的逻辑

    @Override
    public void finishGame() {
        if (heroAircraft.getHp() <= 0) {
            // 游戏结束
            recordTime();
            super.mbLoop = false;
            super.gameOverFlag = true;
            GameOverFlag.gameOverFlag=true;
            Log.i("updateGame","Game Over");
        }
    }
}
