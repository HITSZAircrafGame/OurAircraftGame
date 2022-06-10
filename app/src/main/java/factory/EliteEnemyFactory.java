package factory;

import android.app.Activity;

import com.example.aircraftgame.GameActivity;

import application.ImageManager;
import product.enemy.BaseEnemy;
import product.enemy.EliteEnemy;
import strategy.StraightShoot;

public class EliteEnemyFactory implements EnemyFactory {

    private int eliteHp;  //精英敌机生命值

    //精英敌机单个子弹的威力（随难度成比例改变,目前暂定为15）
    private int elitePower;

    //精英敌机子弹速度（随难度成比例改变，越慢越难（因为慢就密集），目前暂定为12）
    private int eliteBulletSpeed;



    public EliteEnemyFactory(int eliteHp, int elitePower, int eliteBulletSpeed){
        //构造工厂实例
        this.eliteHp = eliteHp;
        this.elitePower = elitePower;
        this.eliteBulletSpeed = eliteBulletSpeed;
    }

    @Override
    public BaseEnemy createEnemy(){
        return new EliteEnemy(
                (int) ( Math.random() * (GameActivity.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth()))*1,
                (int) (Math.random() * GameActivity.WINDOW_HEIGHT * 0.2)*1,
                (Math.random() > 0.5 ? 7:-7)* GameActivity.WINDOW_WIDTH/350,      //不同于普通敌机，有横向速度，且初始横向移动方向随机
                GameActivity.WINDOW_HEIGHT/80,      //不同于普通敌机，纵向速度要慢一些,约为1/3
                eliteHp,     //随难度预设
                new StraightShoot(), //直射方式
                elitePower,  //随难度预设
                eliteBulletSpeed  //随难度预设
        );
    }
}
