package factory;

import com.example.aircraftgame.GameActivity;

import application.ImageManager;
import product.enemy.BaseEnemy;
import product.enemy.MobEnemy;
import strategy.NoShoot;

public class MobEnemyFactory implements EnemyFactory {

    private int mobHp;

    private int mobSpeedY;

    public MobEnemyFactory(int mobHp, int mobSpeedY){
        //构造工厂实例
        this.mobHp = mobHp;
        this.mobSpeedY = mobSpeedY;
    }

    @Override
    public BaseEnemy createEnemy(){
        return new MobEnemy(
                (int) ( Math.random() * (GameActivity.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()))*1,
                (int) (Math.random() * GameActivity.WINDOW_HEIGHT * 0.2)*1,
                0,
                mobSpeedY,
                mobHp,
                new NoShoot() //普通敌机无射击方式，采用NoShoot策略
        );
    }
}
