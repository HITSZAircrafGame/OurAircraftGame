package product.enemy;

import com.example.aircraftgame.GameActivity;

import bullet.BaseBullet;
import strategy.ShootStrategy;

import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends BaseEnemy {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp, ShootStrategy shootWay) {
        super(locationX, locationY, speedX, speedY, hp, shootWay);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= GameActivity.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    /**
     * ver4.0修改
     * 普通敌机不会射击，故全部传入默认参数，在Game处会使用NoShoot策略
     * */
    @Override
    public List<BaseBullet> shoot() {
        return shootWay.doShoot(0, 0, 0, 0, 0,null);
    }
}
