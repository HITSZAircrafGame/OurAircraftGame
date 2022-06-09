package sp_objects;

public class Laser extends SpeObject {

    private static Laser laser;

    private Laser(int locationX, int locationY){
        super(locationX, locationY);
    }

    public static Laser getLaser(double locationX, double locationY){
        if (laser == null){
            synchronized (Laser.class){
                if (laser == null){
                    laser = new Laser((int)locationX, (int)locationY);
                }
            }
        } else {
            synchronized (Laser.class) {
                laser.setLocation((int)locationX, (int)locationY);
            }
        }
        return laser;
    }
}
