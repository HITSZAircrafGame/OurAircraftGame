package com.example.aircraftgame.GameDifficulty;

import android.content.Context;

import com.example.aircraftgame.GameViewTest;

public class EasyGameView extends GameViewTest {
    public EasyGameView(Context context, int screenWidth, int screenHeight) {
        super(context, screenWidth, screenHeight);

        //修改参数
        this.eliteOccur=0.20f;
        heroShootSpeed=600;
        enemyShootSpeed=600;
    }

    @Override
    public boolean hook(){
        return false;
    }

}
