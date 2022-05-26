package com.example.aircraftgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//初始界面
public class StartActivity extends AppCompatActivity {

    private static boolean isOnline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button localPlay = (Button)findViewById(R.id.startLocalButton);
        localPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, MainMenu.class);
                isOnline = false;
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