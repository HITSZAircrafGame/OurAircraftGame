package product.prop;

import com.example.aircraftgame.GameActivity;

import java.util.ArrayList;
import java.util.List;

import Observer.Object;
import Observer.Observer;
import basic.AbstractFlyingObject;

public abstract class BaseProp extends AbstractFlyingObject implements Object {
    List<Observer> observerList;
    public BaseProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
        this.observerList=new ArrayList<>();
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
