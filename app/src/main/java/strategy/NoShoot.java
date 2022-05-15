package strategy;

import aircraft.AbstractAircraft;
import bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

public class NoShoot implements ShootStrategy{
    @Override
    public List<BaseBullet> doShoot(int locationX, int locationY, int power,
                                    int shootNum, int direction, AbstractAircraft aircraft){
        return new LinkedList<>();
    }
}
