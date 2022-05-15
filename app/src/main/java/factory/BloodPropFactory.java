package factory;

import product.prop.BaseProp;
import product.prop.BloodProp;

public class BloodPropFactory implements PropFactory {

    private int bloodPropHeal;

    public BloodPropFactory(int bloodPropHeal){
        //构造工厂实例
        this.bloodPropHeal = bloodPropHeal;
    }

    @Override
    public BaseProp createProp(int locationX, int locationY, int speedX, int speedY){
        return new BloodProp(locationX, locationY, speedX, speedY, bloodPropHeal);
    }
}
