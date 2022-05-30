package com.example.aircraftgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.PictureInPictureUiState;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import login.Player;
import login.PlayerAccess;

//注册界面
public class RegisterViewActivity extends AppCompatActivity {

    private EditText playerNameEdit = null;
    private EditText passwordEdit = null;
    private EditText confPassEdit = null;
    private String passedPlayerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);
        playerNameEdit = (EditText) findViewById(R.id.register_playerName_edit);
        passwordEdit = (EditText) findViewById(R.id.register_password_edit);
        confPassEdit = (EditText) findViewById(R.id.register_confPass_edit);
        Button registerButton = (Button)findViewById(R.id.register_registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        Button cancelButton = (Button) findViewById(R.id.register_cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void register(){
        passedPlayerName = playerNameEdit.getText().toString();
        String passwordReg = passwordEdit.getText().toString();
        String confPassReg = confPassEdit.getText().toString();

        Player player = new Player(passedPlayerName, passwordReg, 0);

        new Thread(){
            @Override
            public void run(){
                int msg = 0;
                PlayerAccess pa = new PlayerAccess();

                Player playerFound = pa.findPlayer(passedPlayerName);
                if(playerFound != null){
                    msg = 1;
                } else {
                    boolean flag = pa.register(player);
                    if(flag){
                        msg = 2;
                    }
                }
                hand.sendEmptyMessage(msg);
            }
        }.start();
    }

    @SuppressLint("HandlerLeak")
    final Handler hand = new Handler(){
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg){
            if(msg.what == 0){
                Toast.makeText(getApplicationContext(),
                        "注册失败！", Toast.LENGTH_SHORT).show();
            } else if(msg.what == 1){
                Toast.makeText(getApplicationContext(),
                        "该玩家名已存在，请换一个玩家名！", Toast.LENGTH_SHORT).show();
            } else if(msg.what == 2){
                Toast.makeText(getApplicationContext(),
                        "注册成功！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterViewActivity.this,
                                            OnlineMenu.class);
                intent.putExtra("playerName", passedPlayerName);
                startActivity(intent);
            }
        }
    };
}


