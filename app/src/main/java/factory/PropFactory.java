package factory;

import product.prop.BaseProp;

public interface PropFactory {
    BaseProp createProp(int locationX, int locationY, int speedX, int speedY);
}
