package product.prop;

import java.util.List;
import Observer.Observer;

public class FireProp extends BaseProp {

    public FireProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }


    @Override
    public void addObserver(List<? extends Observer> observer) {
    }

    @Override
    public <T> void removeObserver(T observer) {

    }


    @Override
    public int notifyAllObserver() {
        return 0;
    }
}
