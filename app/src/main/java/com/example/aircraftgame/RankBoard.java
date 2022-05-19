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
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.List;

import record.RankBoardAdapter;
import record.ScoreBoard;

public class RankBoard extends AppCompatActivity {

    private ScoreBoard sb = new ScoreBoard();
    private String enteredName;
    private RankBoardAdapter rba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_board);

        //为删除按钮添加监听器
        Button deleteButton = (Button)findViewById(R.id.DeleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTip();
            }
        });
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
                            enteredName = editText.getText().toString();
                            int score = GameActivity.getGvt().getScore();
                            String time = GameActivity.getGvt().getTime();
                            sb.addRecord(enteredName, score, time);
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
     * 弹出对话框，提示是否需要删除选中的游玩记录
     **/
    private void deleteTip(){
        rba.checkDeletingRecord();
        List<String> deletingRecordTimeList = rba.getSelectedRecordTimeList();
        AlertDialog alert = null;
        AlertDialog.Builder bulider = null;
        bulider = new AlertDialog.Builder(RankBoard.this)
                .setTitle("删除记录")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("您确定要删除选中的这些记录吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(deletingRecordTimeList.size() == 0){
                            Toast.makeText(RankBoard.this, "未选中任何要删除的记录！",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            rba.deleteSelectedRow();
                            for(int i = 0; i < deletingRecordTimeList.size(); i++){
                                sb.deleteRecords(deletingRecordTimeList.get(i));
                            }
                            Toast.makeText(RankBoard.this, "选中的所有记录已删除",
                                    Toast.LENGTH_SHORT).show();
                            showScoreBoard();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(RankBoard.this, "删除取消",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        alert = bulider.create();
        alert.show();
    }

    private void showScoreBoard(){
        RecyclerView rv = findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rba = new RankBoardAdapter(sb.getAllRecords());
        rv.setAdapter(rba);
    }
}