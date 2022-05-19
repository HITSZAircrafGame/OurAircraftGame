package product.enemy;

import com.example.aircraftgame.GameActivity;

import bullet.BaseBullet;
import strategy.ShootStrategy;

import java.util.List;

/**boss机，暂不具体设定方法*/
public class BossEnemy extends BaseEnemy {

    //单次射击子弹数量（取决于具体效果可能会变，目前暂定5）
    private int shootNum;

    //单个子弹的威力（随难度成比例改变,目前暂定20）
    private int power;

    //敌机子弹向下发射，不变
    private final int direction = 1* GameActivity.WINDOW_HEIGHT/500;

    //敌机子弹速度（随难度成比例改变，越慢越难（因为慢就密集），目前暂定为12）
    private int bulletSpeed;

    public BossEnemy(int locationX, int locationY, int speedX,
                     int speedY, int hp, ShootStrategy shootWay, int shootNum, int power, int bulletSpeed){
        super(locationX, locationY, speedX, speedY, hp, shootWay);
        this.shootNum = shootNum;
        this.power = power;
        this.bulletSpeed = bulletSpeed;
    }

    /**
     * ver4.0添加，boss机的射击方法，散射
     * @return
     */
    @Override
    public List<BaseBullet> shoot(){
        return shootWay.doShoot(locationX, locationY, power, shootNum, direction, this);
    }

    @Override
    public void update(){
        this.decreaseHp(100);
    }
}
