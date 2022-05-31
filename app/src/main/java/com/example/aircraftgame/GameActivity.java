package com.example.aircraftgame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aircraftgame.GameDifficulty.DifficultGameView;
import com.example.aircraftgame.GameDifficulty.EasyGameView;
import com.example.aircraftgame.GameDifficulty.NormalGameView;
import com.example.aircraftgame.NetGame.NetGame;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.LogManager;

import MusicPlayer.MusicServer;
import PublicLockAndFlag.GameBossFlag;
import PublicLockAndFlag.GameHitFlag;
import PublicLockAndFlag.GameOverFlag;
import PublicLockAndFlag.GameSupplyFlag;
import PublicLockAndFlag.OnlineGameOver;
import aircraft.HeroAircraft;
import strategy.StraightShoot;

public class GameActivity extends AppCompatActivity {
    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;

    private static GameViewTest gvt;
    //检查游戏是否需要音效
    private static boolean GameNeedVideo;
    //获取选择的游戏难度
    private static int GameDifficulty;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //检查 游戏的难度 和 游戏是否需要音效，设置 GameDifficulty 和 GameNeedVideo 两个变量
        Intent neededData = getIntent();
        GameNeedVideo = neededData.getBooleanExtra("videoIsNeeded", false);
        GameDifficulty = neededData.getIntExtra("difficulty", 1);
        Log.i("Game",GameNeedVideo+"");
        Log.i("Game",GameDifficulty+"");
        //隐藏标题全屏游玩
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindowSize();
        setContentView(R.layout.activity_main);

        new OnlineGame().start();
//        localGame();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        HeroAircraft.getHeroAircraft(new StraightShoot()).setLocation(event.getX(), event.getY());
        return true;
    }

    public void getWindowSize(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        WINDOW_WIDTH = dm.widthPixels;
        WINDOW_HEIGHT = dm.heightPixels;
    }

    public static GameViewTest getGvt() {
        return gvt;
    }

    /**
    * 调用该静态方法判断游戏是否需要音效
    **/
    public static boolean isGameNeedVideo() {
        return GameNeedVideo;
    }

    /**
    * 调用该静态方法判断本局游戏选择的难度，简单、普通、困难分别对应数字1、2、3
    **/
    public static int getGameDifficulty() {
        return GameDifficulty;
    }

    /**
     * 处理联网游戏的逻辑
     * **/
    public class OnlineGame extends Thread{
      @Override
        public void run(){
          try{
              Log.i("OnlineGame","send connection request");
              Socket socket=new Socket();
              socket.connect(new InetSocketAddress("192.168.56.1",1111),5000);

              Log.i("OnlineGame","玩家连接完毕");
            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
              Log.i("OnlineGame","waiting connection");
            if(in.readLine().equals("true")){
                gvt=new NetGame(GameActivity.this,WINDOW_WIDTH,WINDOW_HEIGHT,socket);
                gvt = new EasyGameView(GameActivity.this, WINDOW_WIDTH, WINDOW_HEIGHT);
                setContentView(gvt);
                startMusic();
            }
              Log.i("OnlineGame","网络游戏开始");
          }catch(Exception e){
              e.printStackTrace();
          }
      }
    }

    /**本地游戏**/
    public void localGame(){
                switch(GameDifficulty){
            case 1:
                Log.i("Game","简单模式");
                gvt = new EasyGameView(this, WINDOW_WIDTH, WINDOW_HEIGHT);
                break;
            case 2:
                Log.i("Game","普通模式");
                gvt = new NormalGameView(this, WINDOW_WIDTH, WINDOW_HEIGHT);
                break;
            case 3:
                Log.i("Game","困难模式");
                gvt = new DifficultGameView(this, WINDOW_WIDTH, WINDOW_HEIGHT);
                break;
            default:
                Log.e("Game","选择错误");

        }

        setContentView(gvt);
        startMusic();
    }

    /**音乐播放**/
    public void startMusic(){
        //设置service播放音乐
        //新建一条线程来执行
        if(GameNeedVideo){
            Log.i("Game","播放音乐");
            Intent intent=new Intent(this, MusicServer.class);
            new Thread(()->{
                while(!GameOverFlag.gameOverFlag){
                    startService(intent);
                }
                stopService(intent);
            }).start();
        }
    }
}