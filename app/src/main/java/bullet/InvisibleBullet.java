package bullet;

public class InvisibleBullet extends BaseBullet{
    public InvisibleBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    @Override
    public void update(){
        vanish();
    }
}
