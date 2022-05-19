package com.example.aircraftgame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
=======
import android.content.Intent;
import android.media.MediaPlayer;
>>>>>>> 29d227887b35b0c2692e7249da07dcf28c4bd364
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import MusicPlayer.MusicServer;
import PublicLockAndFlag.GameBossFlag;
import PublicLockAndFlag.GameHitFlag;
import PublicLockAndFlag.GameOverFlag;
import PublicLockAndFlag.GameSupplyFlag;
import aircraft.HeroAircraft;
import strategy.StraightShoot;

public class GameActivity extends AppCompatActivity {
    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;
<<<<<<< HEAD

    private static GameViewTest gvt;

=======
    private GameViewTest gvt;
>>>>>>> 29d227887b35b0c2692e7249da07dcf28c4bd364
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题全屏游玩
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindowSize();
        setContentView(R.layout.activity_main);
        gvt = new GameViewTest(this, WINDOW_WIDTH, WINDOW_HEIGHT);
        setContentView(gvt);
        //设置service播放音乐
        //新建一条线程来执行
        Intent intent=new Intent(this, MusicServer.class);

        new Thread(()->{
            while(!GameOverFlag.gameOverFlag){
                startService(intent);
            }

            stopService(intent);
        }).start();
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
}