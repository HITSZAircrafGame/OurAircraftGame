package sp_objects;

public class BombEffect{

    private int BombEffFrame = 0;

    public BombEffect(){}

    public int getBombEffFrame() {
        return BombEffFrame;
    }

    public void frameAdd(){
        BombEffFrame++;
    }
}
