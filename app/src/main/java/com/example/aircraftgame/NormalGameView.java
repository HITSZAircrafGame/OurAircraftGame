package com.example.aircraftgame;

import android.content.Context;
import android.util.Log;
import android.graphics.BitmapFactory;

import com.example.aircraftgame.GameActivity;
import com.example.aircraftgame.GameViewTest;

import application.ImageManager;
import factory.BossEnemyFactory;
import factory.EliteEnemyFactory;
import factory.MobEnemyFactory;

public class NormalGameView extends GameViewTest {
    private double rate;
    public NormalGameView(Context context, int screenWidth, int screenHeight) {
        super(context, screenWidth, screenHeight);
        enemyMaxNumber=6;
        enemyShootSpeed=1000;
        heroShootSpeed=480;
        this.eliteOccur=0.25f;
        this.bossHp=500;
        bossScore=500;
        rate=1;
    }

    @Override
    public boolean hook() {
        return true;
    }

    @Override
    public void createEnemy() {
        changeDifficulty();
        if (timeCountAndNewCycleJudge()) {
            // 新敌机产生
            if(hook()){
                if (scoreBound >= bossScore && !bossAlreadyExist) { //ver4.0添加，每当在击破上一个boss机后再次得到200分以上时出现新的boss机
                    bef = new BossEnemyFactory(bossHp, bossPower, bossBulletSpeed, bossShootNum); //ver4.0添加
                    createBoss();
                }
            }
            if (enemyAircrafts.size() < enemyMaxNumber) {  //ver2.0修改过
                mef = new MobEnemyFactory(mobHp,mobSpeedY); //ver2.0添加
                eef = new EliteEnemyFactory(eliteHp,elitePower,eliteBulletSpeed); //ver2.0添加
                createMobAndElite();
            }
        }
    }

    //难度变化函数
    public void changeDifficulty(){
        if(time%2000==0){
            rate=(rate<=2)?rate+(Math.random()<0.5?0.1:0.2):2;
        }

         eliteHp = (int)(60*rate); //ver1.0添加，用于设置精英敌机的生命值（随难度变化，目前暂定为60（两次攻击））
        Log.i("Normal",eliteHp+"");
        elitePower = elitePower>20?20:(int)(super.elitePower*rate); //ver2.0添加，精英敌机单个子弹的威力（随难度成比例改变,目前暂定为15）
    }

    @Override
    public void loadImagesByDiff() {
        ImageManager.BACKGROUND_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.bg4);
        ImageManager.MOB_ENEMY_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.mob3);
        ImageManager.ELITE_ENEMY_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.elite3);
        ImageManager.ENEMY_BULLET_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.bullet_enemy3);
        ImageManager.setUpClassnameImageMap();
    }
}
