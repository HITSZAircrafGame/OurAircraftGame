package factory;

import product.prop.BaseProp;
import product.prop.ShieldProp;

public class ShieldPropFactory implements PropFactory{
    
    public ShieldPropFactory(){}
    
    @Override
    public BaseProp createProp(int locationX, int locationY, int speedX, int speedY){
        return new ShieldProp(locationX, locationY, speedX, speedY);
    }
}
