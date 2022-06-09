package factory;

import product.prop.BaseProp;
import product.prop.LaserProp;

public class LaserPropFactory implements PropFactory{

    public LaserPropFactory(){}

    @Override
    public BaseProp createProp(int locationX, int locationY, int speedX, int speedY){
        return new LaserProp(locationX, locationY, speedX, speedY);
    }
}
