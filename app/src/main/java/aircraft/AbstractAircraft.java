package aircraft;

import basic.AbstractFlyingObject;
import bullet.BaseBullet;
import strategy.ShootStrategy;

import java.util.List;
import Observer.*;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject implements Observer {
    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;
    protected ShootStrategy shootWay; //ver4.0添加

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp,ShootStrategy shootWay) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
        this.shootWay = shootWay; //ver4.0添加
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp){ //ver1.0添加，用于捡起加血道具时给英雄机回复血量
        if(hp >= maxHp) { //防止溢出
            this.hp = maxHp;
        }else if(hp >= 0){
            this.hp = hp;
        }else{ //不可以小于0
            System.out.println("Hp must be greater than or equal to zero!");
        }
    }

    /**
     * ver4.0添加
     * 设定飞机射击方式
     * */
    public void setShootWay(ShootStrategy shootWay){
        this.shootWay = shootWay;
    }

    /**
     *
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */
    public abstract List<BaseBullet> shoot();

    public void update(){
        vanish();
    }

}


