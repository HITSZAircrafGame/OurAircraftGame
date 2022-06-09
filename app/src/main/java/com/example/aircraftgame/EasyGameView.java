package com.example.aircraftgame;

import android.content.Context;
import android.graphics.BitmapFactory;

import application.ImageManager;

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

    @Override
    public void loadImagesByDiff() {
        ImageManager.BACKGROUND_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        ImageManager.MOB_ENEMY_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.mob2);
        ImageManager.ELITE_ENEMY_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.elite2);
        ImageManager.ENEMY_BULLET_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.bullet_enemy2);
    }
}
