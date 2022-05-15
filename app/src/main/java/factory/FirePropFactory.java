package factory;

import product.prop.BaseProp;
import product.prop.FireProp;

public class FirePropFactory implements PropFactory {

    public FirePropFactory(){
        //构造工厂实例
    }

    @Override
    public BaseProp createProp(int locationX, int locationY, int speedX, int speedY){
        return new FireProp(locationX, locationY, speedX, speedY);
    }
}
