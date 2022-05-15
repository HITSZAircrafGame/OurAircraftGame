package strategy;

import aircraft.AbstractAircraft;
import aircraft.HeroAircraft;
import bullet.BaseBullet;
import bullet.EnemyBullet;
import bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class StraightShoot implements ShootStrategy{
    @Override
    public List<BaseBullet> doShoot(int locationX, int locationY,
                                    int power, int shootNum, int direction, AbstractAircraft aircraft){
        List<BaseBullet> bullets = new LinkedList<>();
        int x = locationX;
        int y = locationY + direction * 2;
        int speedX = 0;
        int speedY = aircraft.getSpeedY()+direction*5;
        BaseBullet bullet = null;
        if (aircraft instanceof HeroAircraft){
            for (int i = 0; i < shootNum; i++){
                bullet = new HeroBullet(x, y, speedX, speedY, power);
                bullets.add(bullet);
            }
        } else {
            for (int i = 0; i < shootNum; i++){
                bullet = new EnemyBullet(x, y, speedX, speedY, power);
                bullets.add(bullet);
            }
        }
        return bullets;
    }
}
