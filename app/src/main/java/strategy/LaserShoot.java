package strategy;

import java.util.LinkedList;
import java.util.List;

import aircraft.AbstractAircraft;
import bullet.BaseBullet;
import bullet.InvisibleBullet;

public class LaserShoot implements ShootStrategy{
    @Override
    public List<BaseBullet> doShoot(int locationX, int locationY,
                                    int power, int shootNum, int direction, AbstractAircraft aircraft) {
        List<BaseBullet> bullets = new LinkedList<>();
        int x = locationX;
        int y = locationY + direction * 2;
        int speedX = 0;
        int speedY = aircraft.getSpeedY() + direction * 50;
        BaseBullet bullet = null;
        for (int i = 0; i < shootNum; i++) {
            bullet = new InvisibleBullet(x, y, speedX, speedY, 200);
            bullets.add(bullet);
            bullets.add(bullet);
        }
        return bullets;
    }
}
