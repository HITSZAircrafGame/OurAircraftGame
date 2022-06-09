package com.example.aircraftgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainMenu extends AppCompatActivity implements ButtonAnimation{

    private boolean videoOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_menu);
        Button easyButton = (Button)findViewById(R.id.easyButton);

        Intent intent = new Intent(MainMenu.this,GameActivity.class);
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initButton(easyButton);
                int easyFlag = 1;
                intent.putExtra("difficulty", easyFlag);
                startActivity(intent);
            }
        });

        Button mediumButton = (Button)findViewById(R.id.mediumButton);
        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initButton(mediumButton);
                int mediumFlag = 2;
                intent.putExtra("difficulty", mediumFlag);
                startActivity(intent);
            }
        });

        Button hardButton = (Button)findViewById(R.id.hardButton);
        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initButton(hardButton);
                int hardFlag = 3;
                intent.putExtra("difficulty", hardFlag);
                startActivity(intent);
            }
        });

        Switch s = (Switch)findViewById(R.id.videoSwitch);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    videoOn = true;
                    intent.putExtra("videoIsNeeded",videoOn);
                } else {
                    intent.putExtra("videoIsNeeded", videoOn);
                }
            }
        });
    }
}