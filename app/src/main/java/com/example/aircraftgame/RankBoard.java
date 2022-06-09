package com.example.aircraftgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import record.RankBoardAdapter;
import record.ScoreBoard;

public class RankBoard extends AppCompatActivity {

    private ScoreBoard sb;
    private String difficultyString;
    private RankBoardAdapter rba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_rank_board);

        //转化难度字符串
        setDifficultyString();

        //创建排行榜对象
        sb = new ScoreBoard(difficultyString);

//        //为删除按钮添加监听器
//        deleteButtonSetListener();

        //设置排行榜显示的难度
        showDifficulty();

        //先弹出对话框看要不要输入姓名记录成绩
        recordTip();

        //利用recyclerView生成排行榜UI界面
        showScoreBoard();
    }

    /**
     * 弹出对话框，提示是否需要记录本次游玩记录
     **/
    private void recordTip(){
        AlertDialog alert = null;
        AlertDialog.Builder bulider = null;
        EditText editText = new EditText(RankBoard.this);
        bulider = new AlertDialog.Builder(RankBoard.this)
                    .setTitle("记录成绩")
                    .setIcon(R.mipmap.ic_launcher)
                    .setMessage("请输入您的姓名后点击 '确定' 键以记录本次游玩成绩（若不记录，请按 '取消' 键直接转到排行榜界面）")
                    .setView(editText)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String enteredName = editText.getText().toString();
                            int score = GameActivity.getGvt().getScore();
                            String time = GameActivity.getGvt().getTime();
                            sb.addRecord(enteredName, score, time, difficultyString);
                            Toast.makeText(RankBoard.this, "您的本次游玩成绩已被记录",
                                            Toast.LENGTH_SHORT).show();
                            showScoreBoard();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(RankBoard.this, "未记录本次游玩成绩",
                                            Toast.LENGTH_SHORT).show();
                        }
                    });
        alert = bulider.create();
        alert.show();
    }

    /**
     * 显示排行榜具体界面
     * */
    private void showScoreBoard(){
        RecyclerView rv = findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rba = new RankBoardAdapter(RankBoard.this, sb, sb.getAllRecords());
        rv.setAdapter(rba);
    }

    /**
     * 显示难度标签
     * */
    private void showDifficulty(){
        TextView tv = (TextView)findViewById(R.id.text_difficulty);
        switch (GameActivity.getGameDifficulty()){
            case 1: {
                tv.setText("难度：简单");
                break;
            }
            case 2: {
                tv.setText("难度：中等");
                break;
            }
            case 3: {
                tv.setText("难度：困难");
                break;
            }
        }
    }

    /**
     * 将游戏难度转换为字符串存储到玩家游玩记录中
     * */
    private void setDifficultyString(){
        switch (GameActivity.getGameDifficulty()){
            case 1: {
                difficultyString = "简单";
                break;
            }
            case 2: {
                difficultyString = "中等";
                break;
            }
            case 3: {
                difficultyString = "困难";
                break;
            }
        }
    }
}