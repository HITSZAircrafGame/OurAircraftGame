package product.enemy;

import Observer.Observer;
import aircraft.AbstractAircraft;
import strategy.ShootStrategy;

public abstract class BaseEnemy extends AbstractAircraft implements Observer {
    public BaseEnemy(int locationX, int locationY, int speedX, int speedY, int hp, ShootStrategy shootWay){
        super(locationX, locationY, speedX, speedY, hp, shootWay);
    }
    public void update(){
        vanish();
    }
}
