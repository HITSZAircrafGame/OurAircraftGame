package application;

import android.graphics.Bitmap;

import bullet.InvisibleBullet;
import bullet.MagicArrow;
import product.enemy.BossEnemy;
import product.prop.BloodProp;
import product.prop.BombProp;
import product.prop.FireProp;
import product.enemy.EliteEnemy;
import aircraft.HeroAircraft;
import product.enemy.MobEnemy;
import bullet.EnemyBullet;
import bullet.HeroBullet;
import product.prop.LaserProp;
import product.prop.ShieldProp;
import sp_objects.BonusStar;
import sp_objects.Laser;
import sp_objects.Shield;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 综合管理图片的加载，访问
 * 提供图片的静态访问方法
 *
 * @author hitsz
 */
public class ImageManager {

    /**
     * 类名-图片 映射，存储各基类的图片 <br>
     * 可使用 CLASSNAME_IMAGE_MAP.get( obj.getClass().getName() ) 获得 obj 所属基类对应的图片
     */
    private static final Map<String, Bitmap> CLASSNAME_IMAGE_MAP = new HashMap<>();

    public static Bitmap BACKGROUND_IMAGE;

    public static Bitmap HERO_IMAGE;

    public static Bitmap HERO_BULLET_IMAGE;
    public static Bitmap ENEMY_BULLET_IMAGE;
    public static Bitmap INVISIBLE_BULLET_IMAGE;

    public static Bitmap MOB_ENEMY_IMAGE;
    public static Bitmap ELITE_ENEMY_IMAGE;
    public static Bitmap BOSS_ENEMY_IMAGE;

    public static Bitmap PROP_BLOOD_IMAGE;
    public static Bitmap PROP_BOMB_IMAGE;
    public static Bitmap PROP_BULLET_IMAGE;
    public static Bitmap PROP_SHIELD_IMAGE;
    public static Bitmap PROP_LASER_IMAGE;

    public static Bitmap MAGIC_ARROW_IMAGE;
    public static Bitmap SHIELD_IMAGE;
    public static Bitmap MIPMAP_BONUS_IMAGE;

    //积分商城的三张图片
    public static Bitmap BONUS_SHIELD_IMAGE;
    public static Bitmap BONUS_LASER_IMAGE;
    public static Bitmap BONUS_BOMB_IMAGE;

    //激光动画的四帧图片
    public static List<Bitmap> LASER_FRAMES;

    //爆炸动画的九帧图片
    public static List<Bitmap> BOMB_FRAMES;





    

    public static Bitmap get(String className){
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static Bitmap get(Object obj){
        if (obj == null){
            return null;
        }
        return get(obj.getClass().getName());
    }

    public static void setUpClassnameImageMap(){
        setUpHeroImages();
        setUpEnemyImages();
        setUpBulletImages();
        setUpPropImages();
        setUpSpeObjImages();
    }

    /**
     * 设置英雄机图片，此处可扩展
     * */
    public static void setUpHeroImages(){
        CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);
    }

    /**
     * 设置所有敌机图片，此处可扩展
     * */
    public static void setUpEnemyImages(){
        CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), MOB_ENEMY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(), ELITE_ENEMY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(BossEnemy.class.getName(), BOSS_ENEMY_IMAGE);
    }

    /**
     * 设置所有子弹图片，此处可扩展
     * */
    public static void setUpBulletImages(){
        CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
        CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);
        CLASSNAME_IMAGE_MAP.put(InvisibleBullet.class.getName(), INVISIBLE_BULLET_IMAGE);
        CLASSNAME_IMAGE_MAP.put(MagicArrow.class.getName(), MAGIC_ARROW_IMAGE);
    }

    /**
     * 设置所有道具图片，此处可扩展
     * */
    public static void setUpPropImages(){
        CLASSNAME_IMAGE_MAP.put(BloodProp.class.getName(), PROP_BLOOD_IMAGE);
        CLASSNAME_IMAGE_MAP.put(BombProp.class.getName(), PROP_BOMB_IMAGE);
        CLASSNAME_IMAGE_MAP.put(FireProp.class.getName(), PROP_BULLET_IMAGE);
        CLASSNAME_IMAGE_MAP.put(ShieldProp.class.getName(), PROP_SHIELD_IMAGE);
        CLASSNAME_IMAGE_MAP.put(LaserProp.class.getName(), PROP_LASER_IMAGE);
    }

    /**
     * 设置所有特殊物体图片，此处可扩展
     * */
    public static void setUpSpeObjImages(){
        CLASSNAME_IMAGE_MAP.put(Shield.class.getName(), SHIELD_IMAGE);
    }
}
