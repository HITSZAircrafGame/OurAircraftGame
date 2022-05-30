package com.example.aircraftgame.NetGame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.Log;

import com.example.aircraftgame.GameDifficulty.DifficultGameView;
import com.example.aircraftgame.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

//Online Playing Model
public class NetGame extends DifficultGameView {
    //另一个玩家的分数
    public int score2;
    public Socket socket;
    public NetGame(Context context, int screenWidth, int screenHeight,Socket socket) {
        super(context, screenWidth, screenHeight);
        score2=0;
        this.socket=socket;

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
                while(!((content=in.readLine()).equals("Over"))){
                    //游戏未结束，持续输送信号
                    Log.i("The Other Player Score",content);
                    score2=Integer.parseInt(content);
                    Log.i("Local Player Score",score+"");
                    out.println(score);
                }
                //设置一个双方游戏结束标志
                //TODO

            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
