package com.example.aircraftgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Toast;

//初始界面
public class StartActivity extends AppCompatActivity implements ButtonAnimation{

    private static boolean isOnline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_start);

        Button localPlay = (Button)findViewById(R.id.startLocalButton);
        localPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, MainMenu.class);
                isOnline = false;
                initButton(localPlay);
                Toast.makeText(StartActivity.this,
                        "您选择了本地游玩，现在就开始游戏吧", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        Button onlinePlay = (Button)findViewById(R.id.startOnlineButton);
        onlinePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, LoginViewActivity.class);
                isOnline = true;
                initButton(onlinePlay);
                Toast.makeText(StartActivity.this,
                        "您选择了在线游玩，请先登录", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

    public static boolean getIsOnline() {
        return isOnline;
    }
}