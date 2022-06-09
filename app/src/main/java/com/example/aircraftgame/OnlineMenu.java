package com.example.aircraftgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

//在线游玩的选择界面
public class OnlineMenu extends AppCompatActivity {

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
    }
}