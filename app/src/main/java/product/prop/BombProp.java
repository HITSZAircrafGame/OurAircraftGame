package product.prop;

import java.util.ArrayList;
import java.util.List;
import Observer.*;
import aircraft.AbstractAircraft;
import basic.AbstractFlyingObject;
import product.enemy.BossEnemy;
import product.enemy.EliteEnemy;
import product.enemy.MobEnemy;

public class BombProp extends BaseProp {
    private List<Observer> observerList;
    public BombProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
        observerList=new ArrayList<Observer>();

    }


    @Override
    public void addObserver(List<? extends Observer> observer) {
        observerList.addAll(observer);
    }

    @Override
    public int notifyAllObserver() {
        int score=0;//分数
        for(int i=0;i<observerList.size();i++){
            observerList.get(i).update();
            if(observerList.get(i) instanceof MobEnemy){
                score+=10;
            }
            else if(observerList.get(i) instanceof EliteEnemy){
                score+=50;
            }
            else if(observerList.get(i) instanceof BossEnemy&&((BossEnemy)(observerList.get(i))).notValid()){
                score+=100;
            }
        }
        return score;
    }

    @Override
    public <T> void removeObserver(T observer) {

    }
}
