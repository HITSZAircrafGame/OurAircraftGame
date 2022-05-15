package strategy;

import aircraft.AbstractAircraft;
import bullet.BaseBullet;

import java.util.List;

public interface ShootStrategy {
    List<BaseBullet> doShoot(int locationX, int locationY,
                             int power, int shootNum, int direction, AbstractAircraft aircraft);
}
