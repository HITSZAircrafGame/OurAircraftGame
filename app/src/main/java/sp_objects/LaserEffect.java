package sp_objects;

import basic.AbstractFlyingObject;

public class LaserEffect extends AbstractFlyingObject {
    private static LaserEffect lasEff;

    private LaserEffect(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }

    public static LaserEffect getLaserEffect(double locationX, double locationY){
        if (lasEff == null){
            synchronized (Shield.class){
                if (lasEff == null){
                    //无速度，跟据玩家操作移动而移动，与英雄机是一样的
                    lasEff = new LaserEffect((int)locationX, (int)locationY, 0, 0);
                }
            }
        } else {
            synchronized (LaserEffect.class) {
                lasEff.setLocation(locationX, locationY);
            }
        }
        return lasEff;
    }
}
