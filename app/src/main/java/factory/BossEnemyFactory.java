package factory;

import android.app.Activity;

import com.example.aircraftgame.GameActivity;

import application.ImageManager;
import product.enemy.BaseEnemy;
import strategy.ScatterShoot;
import product.enemy.BossEnemy;

public class BossEnemyFactory implements EnemyFactory {

    private int bossHp; //暂时定为120

    private int bossPower; //暂时定为20

    private int bossBulletSpeed; //暂时定为12

    private int bossShootNum; //暂时定为5

    public BossEnemyFactory(int bossHp, int bossPower, int bossBulletSpeed, int bossShootNum){
        //构造工厂实例
        this.bossHp = bossHp;
        this.bossPower = bossPower;
        this.bossBulletSpeed = bossBulletSpeed;
        this.bossShootNum = bossShootNum;
    }

    /**ver4.0设置**/
    @Override
    public BaseEnemy createEnemy(){
        return new BossEnemy(
                GameActivity.WINDOW_WIDTH / 2,
                ImageManager.BOSS_ENEMY_IMAGE.getHeight() - 100,
                (Math.random() > 0.5 ? 7:-7)* GameActivity.WINDOW_WIDTH/300,      //Boss机略大于精英敌机，移动稍慢
                0,      //boss机无纵向移动速度
                200,    //随难度预设
                new ScatterShoot(), //射击方式，散射
                bossShootNum, //随难度预设
                bossPower,  //随难度预设
                bossBulletSpeed  //随难度预设
        );
    }
}
