package sp_objects;

import basic.AbstractFlyingObject;

/**
 * 护盾和英雄机类似，也是单例模式
 * */
public class Shield extends AbstractFlyingObject {

    private static Shield shield;

    private Shield(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }

    public static Shield getShield(double locationX, double locationY){
        if (shield == null){
            synchronized (Shield.class){
                if (shield == null){
                    //无速度，跟据玩家操作移动而移动，与英雄机是一样的
                    shield = new Shield((int)locationX, (int)locationY, 0, 0);
                }
            }
        } else {
            synchronized (Shield.class) {
                shield.setLocation(locationX, locationY);
            }
        }
        return shield;
    }
}
