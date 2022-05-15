package factory;

import product.prop.BaseProp;
import product.prop.BombProp;

public class BombPropFactory implements PropFactory {

    public BombPropFactory(){
        //构造工厂实例
    }

    @Override
    public BaseProp createProp(int locationX, int locationY, int speedX, int speedY){
        return new BombProp(locationX, locationY, speedX, speedY);
    }
}
