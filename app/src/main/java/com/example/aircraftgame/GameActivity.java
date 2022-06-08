package com.example.aircraftgame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import MusicPlayer.MusicServer;
import PublicLockAndFlag.GameOverFlag;
import aircraft.HeroAircraft;
import sp_objects.Laser;
import sp_objects.Shield;
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

    @Override
    public boolean onTouchEvent(MotionEvent event){
        HeroAircraft.getHeroAircraft(new StraightShoot()).setLocation(event.getX(), event.getY());
        Shield.getShield(event.getX(), event.getY() - 100);
        Laser.getLaser(event.getX(), event.getY() - 20);
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
}