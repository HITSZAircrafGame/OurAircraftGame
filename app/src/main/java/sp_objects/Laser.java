package sp_objects;

import basic.AbstractFlyingObject;

public class Laser extends AbstractFlyingObject {

    private static Laser laser;

    private Laser(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }

    public static Laser getLaser(double locationX, double locationY){
        if (laser == null){
            synchronized (Laser.class){
                if (laser == null){
                    //无速度，跟据玩家操作移动而移动，与英雄机是一样的
                    laser = new Laser((int)locationX, (int)locationY, 0, 0);
                }
            }
        } else {
            synchronized (Laser.class) {
                laser.setLocation(locationX, locationY);
            }
        }
        return laser;
    }
}
