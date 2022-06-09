package com.example.aircraftgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//在线游玩的选择界面
public class OnlineMenu extends AppCompatActivity implements ButtonAnimation {
    private String TAG="OnlineMenu";
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_online_menu);
        TextView tv = (TextView) findViewById(R.id.welcomeText);
        String passedPlayerName = getIntent().getStringExtra("playerName");
        tv.setText("尊敬的 " + passedPlayerName + "，欢迎您的到来");
        Button onlineButton=(Button)findViewById(R.id.onlineBattleButton);
        onlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"isOnline: "+StartActivity.getIsOnline());
                Intent intent=new Intent(OnlineMenu.this,GameActivity.class);
                startActivity(intent);
            }
        });
        Button exerciseButton=(Button)findViewById(R.id.practiceModeButton);
        exerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OnlineMenu.this,"未实现训练模式",Toast.LENGTH_SHORT).show();
            }
        });
    }
}