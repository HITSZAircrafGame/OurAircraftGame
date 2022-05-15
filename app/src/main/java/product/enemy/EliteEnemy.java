package product.enemy;

import com.example.aircraftgame.GameActivity;

import bullet.BaseBullet;
import strategy.ShootStrategy;

import java.util.List;

/*精英敌机,可射击*/
public class EliteEnemy extends BaseEnemy {

    //单次射击子弹数量（取决于具体效果可能会变，目前暂定为1）
    private int shootNum = 1;

    //单个子弹的威力（随难度成比例改变,目前暂定为15）
    private int power;

    //敌机子弹向下发射，不变
    private final int direction =GameActivity.WINDOW_HEIGHT/500;

    //敌机子弹速度（随难度成比例改变，越慢越难（因为慢就密集），目前暂定为12）
    private int bulletSpeed;

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp, ShootStrategy shootWay,
                      int power, int bulletSpeed){
        super(locationX, locationY, speedX, speedY, hp, shootWay);
        this.power = power;
        this.bulletSpeed = bulletSpeed;
    }

    //ver2.0由于使用工厂模式而添加
    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= GameActivity.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    //与英雄机的shoot方法大同小异，只不过发射的子弹的属性由EnemyBullet类控制
    public List<BaseBullet> shoot() {
        return shootWay.doShoot(locationX, locationY, power,shootNum, direction, this);
    }
}
