package com.example.aircraftgame.NetGame;

import org.json.JSONObject;

//玩家信息
public class PlayerInfo {
    public static JSONObject playerInfo=new JSONObject();
    static{
        //初始化JSON
        try{
            playerInfo.put("PlayerName","Player");
            playerInfo.put("PassWord","0000");
            playerInfo.put("Score",0);
            playerInfo.put("OpponentName","Opponent");
            playerInfo.put("Score2",0);
            playerInfo.put("GameOver",false);//表示该玩家游戏结束
            playerInfo.put("OnlineDisconnect",false);//表示双方游戏结束，连接断开

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //初始化JSON，每次游戏前都这样干
    public static void init(){
        try{
            playerInfo.put("Score",0);
            playerInfo.put("Score2",0);
            playerInfo.put("GameOver",false);//表示该玩家游戏结束
            playerInfo.put("OnlineDisconnect",false);//表示双方游戏结束，连接断开
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
