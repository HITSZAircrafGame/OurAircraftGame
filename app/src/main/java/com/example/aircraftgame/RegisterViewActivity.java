package com.example.aircraftgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.PictureInPictureUiState;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import login.Player;
import login.PlayerAccess;

//注册界面
public class RegisterViewActivity extends AppCompatActivity implements ButtonAnimation {

    private EditText playerNameEdit = null;
    private EditText passwordEdit = null;
    private EditText confPassEdit = null;
    private String passedPlayerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register_view);
        playerNameEdit = (EditText) findViewById(R.id.register_playerName_edit);
        passwordEdit = (EditText) findViewById(R.id.register_password_edit);
        confPassEdit = (EditText) findViewById(R.id.register_confPass_edit);
        Button registerButton = (Button) findViewById(R.id.register_registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initButton(registerButton);
                register();
            }
        });
        Button cancelButton = (Button) findViewById(R.id.register_cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initButton(cancelButton);
                finish();
            }

        });
    }

    public void register() {
        String enteredName = playerNameEdit.getText().toString();
        String enteredPass = passwordEdit.getText().toString();
        String enteredconfPass = confPassEdit.getText().toString();
        PlayerAccess pa = new PlayerAccess();
        Player foundPlayer = pa.findPlayer(enteredName);
        if (foundPlayer == null) {
            if (enteredPass.equals(enteredconfPass)) {
                pa.addPlayer(enteredName, enteredPass);
                Toast.makeText(getApplicationContext(),
                        "注册成功！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterViewActivity.this,
                        OnlineMenu.class);
                intent.putExtra("playerName", enteredName);
                startActivity(intent);
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "该玩家名已存在，请换一个玩家名！", Toast.LENGTH_SHORT).show();
        }
    }
}




