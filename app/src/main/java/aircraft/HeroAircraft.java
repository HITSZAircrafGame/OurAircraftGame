package aircraft;

import com.example.aircraftgame.GameActivity;

import application.ImageManager;
import bullet.BaseBullet;
import strategy.ShootStrategy;

import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    //ver2.0添加:单例模式，唯一的实例在类的里面
    private volatile static HeroAircraft heroAircraft;

    /**攻击方式 */

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 子弹伤害
     */
    private int power = 30;

    /**
     * 子弹射击方向 (向上发射：-1，向下发射：1)
     */
    private int direction = -1*GameActivity.WINDOW_HEIGHT/500;

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp, ShootStrategy shootWay) {
        super(locationX, locationY, speedX, speedY, hp, shootWay);
    }

    public static HeroAircraft getHeroAircraft(ShootStrategy shootWay){
        if (heroAircraft == null){
            synchronized (HeroAircraft.class){
                if (heroAircraft == null){
                    heroAircraft = new HeroAircraft(
                            GameActivity.WINDOW_WIDTH / 2,
                            GameActivity.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                            0, 0, 1000, shootWay);
                }
            }
        }
        return heroAircraft;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    /**
     * ver4.0添加
     * 可获得shootNum字段用于火力道具
     * */
    public int getShootNum(){
        return this.shootNum;
    }

    /**
     * ver4.0添加
     * 可修改shootNum字段用于散射
     * */
    public void setShootNum(int shootNum){
        this.shootNum = shootNum;
    }

    @Override
    /**
     * ver4.0修改
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        return shootWay.doShoot(locationX, locationY, power, shootNum, direction, this);
    }
}
