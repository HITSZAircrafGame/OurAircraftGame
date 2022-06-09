package com.example.aircraftgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import login.Player;
import login.PlayerAccess;

//登录界面
public class LoginViewActivity extends AppCompatActivity implements ButtonAnimation{

    private String passedPlayerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_view);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initButton(loginButton);
                login();
            }
        });
        Button registerButton = (Button) findViewById(R.id.login_registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initButton(registerButton);
                Intent intent = new Intent(LoginViewActivity.this,
                                            RegisterViewActivity.class);
                startActivity(intent);
            }
        });
    }

    public void login(){
        EditText playerNameEdit = (EditText)findViewById(R.id.login_playerName_edit);
        EditText passwordEdit = (EditText)findViewById(R.id.login_password_edit);
        String editedName = playerNameEdit.getText().toString();
        String editedPass = passwordEdit.getText().toString();
        PlayerAccess pa = new PlayerAccess();
        Player foundPlayer = pa.findPlayer(editedName);
        if(foundPlayer != null){
            if(editedPass.equals(foundPlayer.getPassword())){
                Toast.makeText(getApplicationContext(),
                        "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginViewActivity.this,
                        OnlineMenu.class);
                intent.putExtra("playerName", editedName);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(),
                        "密码错误，请检查密码", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(),
                        "账号未注册，请先注册", Toast.LENGTH_SHORT).show();
        }
    }
}










