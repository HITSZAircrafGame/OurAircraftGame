package product.enemy;

import aircraft.AbstractAircraft;
import strategy.ShootStrategy;

public abstract class BaseEnemy extends AbstractAircraft {
    public BaseEnemy(int locationX, int locationY, int speedX, int speedY, int hp, ShootStrategy shootWay){
        super(locationX, locationY, speedX, speedY, hp, shootWay);
    }
}
