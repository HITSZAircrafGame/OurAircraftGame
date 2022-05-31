package com.example.aircraftgame.NetGame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.Log;

import com.example.aircraftgame.GameDifficulty.DifficultGameView;
import com.example.aircraftgame.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import PublicLockAndFlag.GameOverFlag;
import PublicLockAndFlag.OnlineGameOver;

//Online Playing Model
public class NetGame extends DifficultGameView {
    //另一个玩家的分数
    public int score2;
    public Socket socket;
    public JSONObject playerInfo;
    public NetGame(Context context, int screenWidth, int screenHeight,Socket socket) {
        super(context, screenWidth, screenHeight);
        score2=0;
        this.socket=socket;
        playerInfo=new JSONObject();

        //初始化JSON
        try{
            playerInfo.put("PlayerName","Player");
            playerInfo.put("Score",score);
            playerInfo.put("OpponentName","Opponent");
            playerInfo.put("Score2",score2);
            playerInfo.put("GameOver",false);//表示该玩家游戏结束
            playerInfo.put("OnlineDisconnect",false);//表示双方游戏结束，连接断开
        }catch(Exception e){
            e.printStackTrace();
        }

        OnlineGameOver.flag=false;
        //开启服务线程
        new Client(socket).start();
    }

    @SuppressLint("ResourceAsColor")
    protected void paintScoreAndLife(Canvas cvs) {
        int x = 10;
        int y = 100;
        mPaint.setColor(R.color.red);
        mPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD));
        mPaint.setTextSize(100);
        cvs.drawText("SCORE:" + this.score, x, y, mPaint);
        y = y + 100;
        cvs.drawText("LIFE:" + this.heroAircraft.getHp(), x, y, mPaint);

        //对手
        x=screenWidth-400;
        y=100;
        mPaint.setColor(R.color.light_blue_400);
        cvs.drawText("opponent Player",x,y,mPaint);
        x=screenWidth-200;
        y+=100;
        cvs.drawText("SCORE:"+score2,x,y,mPaint);
    }



    //服务线程
     class Client extends Thread{
        BufferedReader in;
        PrintWriter out;
        public Client(Socket socket){
            try{
                in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void run(){
            //数据交换
            try{
                String content;
                //在双方游戏结束前时刻监听信号

                //发送初始化请求
                if(GameOverFlag.gameOverFlag){
                    playerInfo.put("GameOver",true);
                    Log.i("OnlineGame","Waiting the other player over");
                }
                out.println(playerInfo);
                out.flush();

                while((content=in.readLine())!=null){
                    Log.i("The Other Player Info",content);
                    playerInfo=new JSONObject(content);

                    //游戏未结束，持续输送信号
                    Log.i("The Other Player Score",playerInfo.getInt("Score2")+"");
                    score2=playerInfo.getInt("Score2");
                    Log.i("Local Player Score",score+"");
                    playerInfo.put("Score",score);
                    if(GameOverFlag.gameOverFlag){
                        playerInfo.put("GameOver",true);
                        Log.i("OnlineGame","Waiting the other player over");
                    }
                    out.println(playerInfo);
                    out.flush();
                }
                //设置一个双方游戏结束标志
                //TODO
                playerInfo.put("OnlineDisconnect",true);
                OnlineGameOver.flag=true;
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
