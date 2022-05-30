package com.example.aircraftgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ThemedSpinnerAdapter;
import android.widget.Toast;

import java.util.concurrent.ThreadFactory;

import login.Player;
import login.PlayerAccess;

//登录界面
public class LoginViewActivity extends AppCompatActivity {

    private String passedPlayerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        Button registerButton = (Button) findViewById(R.id.login_registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginViewActivity.this,
                                            RegisterViewActivity.class);
                startActivity(intent);
            }
        });
    }

    public void login(){
        EditText playerNameEdit = (EditText)findViewById(R.id.login_playerName_edit);
        EditText passwordEdit = (EditText)findViewById(R.id.login_password_edit);
        passedPlayerName = playerNameEdit.getText().toString();
        new Thread(){
            @Override
            public void run(){
                PlayerAccess pa = new PlayerAccess();
                int msg = pa.login(passedPlayerName, passwordEdit.getText().toString());
                hand1.sendEmptyMessage(msg);
            }
        }.start();
    }

    @SuppressLint("HandlerLeak")
    final Handler hand1 = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if (msg.what == 0){
                Toast.makeText(getApplicationContext(),
                        "登陆失败", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 1){
                Toast.makeText(getApplicationContext(),
                        "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginViewActivity.this,
                        OnlineMenu.class);
                intent.putExtra("playerName", passedPlayerName);
                startActivity(intent);
            } else if (msg.what == 2){
                Toast.makeText(getApplicationContext(),
                        "密码错误，请检查密码", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 3){
                Toast.makeText(getApplicationContext(),
                        "账号未注册，请先注册", Toast.LENGTH_SHORT).show();
            }

        }
    };
}










