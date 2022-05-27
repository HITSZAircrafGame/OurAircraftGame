package com.example.aircraftgame.GameDifficulty;

import android.content.Context;
import android.util.Log;

import com.example.aircraftgame.GameViewTest;

import factory.BossEnemyFactory;
import factory.EliteEnemyFactory;
import factory.EnemyFactory;
import factory.MobEnemyFactory;

public class DifficultGameView extends GameViewTest {
    private double rate;
    public DifficultGameView(Context context, int screenWidth, int screenHeight) {
        super(context, screenWidth, screenHeight);
        enemyMaxNumber=7;
        enemyShootSpeed=800;
        heroShootSpeed=400;
        this.eliteOccur=0.3f;
        this.bossHp=1000;
        bossScore=666;
        rate=1;
        bossPower=30;
        heroAircraft.setPower(50);
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

                    bef = new BossEnemyFactory(bossHp, bossPower, bossBulletSpeed, bossShootNum);
                    Log.i("BossHp",bossHp+"");
                    createBoss();
                    bossPower=bossPower<50?bossPower+10:bossPower;
                    bossHp=bossHp+(int)(Math.random()*100);
                }
            }
            if (enemyAircrafts.size() < enemyMaxNumber) {  //ver2.0修改过
                mef = new MobEnemyFactory(mobHp,mobSpeedY); //ver2.0添加
                eef = new EliteEnemyFactory(eliteHp,elitePower,eliteBulletSpeed);
                createMobAndElite();
            }
        }
    }

    //难度变化函数
    public void changeDifficulty(){
        if(time%1200==0){
            rate=(rate<=3)?rate+(Math.random()<0.5?0.1:0.2):rate;
        }

        eliteHp = (int)(66*rate); //ver1.0添加，用于设置精英敌机的生命值（随难度变化，目前暂定为60（两次攻击））
        elitePower = elitePower>30?30:(int)(20*rate); //ver2.0添加，精英敌机单个子弹的威力（随难度成比例改变,目前暂定为15）
        Log.i("Power",elitePower+"");

    }

}
