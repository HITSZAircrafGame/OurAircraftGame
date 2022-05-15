package product.prop;

import com.example.aircraftgame.GameActivity;

import basic.AbstractFlyingObject;

public abstract class BaseProp extends AbstractFlyingObject {

    public BaseProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void forward() {
        super.forward();

        // 判定 y 轴出界（不考虑向上飞行出界，默认为只会向下飞行）
        if (speedY > 0 && locationY >= GameActivity.WINDOW_HEIGHT ) {
            // 向下飞行出界
            vanish();
        }
    }
}
