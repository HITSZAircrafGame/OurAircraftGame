package product.prop;

import java.util.List;
import Observer.Observer;

public class BloodProp extends BaseProp {

    private int bloodHeal;

    public BloodProp(int locationX, int locationY, int speedX, int speedY, int bloodHeal) {
        super(locationX, locationY, speedX, speedY);
        this.bloodHeal = bloodHeal;
    }

    /**得到可恢复的血量值*/
    public int getBloodHeal(){
        return this.bloodHeal;
    }

    /**根据难度设定可恢复的血量值，暂不定义*/
    public void setBloodHealByLevel(int level){}


    @Override
    public void addObserver(List<? extends  Observer> observer) {
    }

    @Override
    public int notifyAllObserver() {
        return 0;
    }

    @Override
    public <T> void removeObserver(T observer) {

    }
}