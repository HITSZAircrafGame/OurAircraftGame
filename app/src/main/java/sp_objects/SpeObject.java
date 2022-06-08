package sp_objects;

/**
 * 非飞行物类物体，特殊物体的父类，不需要处理碰撞事件
 * 可能有多种用途，但至少要显示在画面中，所以提供位置且位置可变
 * */
public class SpeObject {
    protected int locationX;
    protected int locationY;

    protected SpeObject(int locationX, int locationY){
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocation(int locationX, int locationY){
        this.locationX = locationX;
        this.locationY = locationY;
    }
}
