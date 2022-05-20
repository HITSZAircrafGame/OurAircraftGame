package com.example.aircraftgame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import PublicLockAndFlag.GameBombFlag;
import PublicLockAndFlag.GameBossFlag;
import PublicLockAndFlag.GameFireSupplyLock;
import PublicLockAndFlag.GameHitFlag;
import PublicLockAndFlag.GameOverFlag;
import PublicLockAndFlag.GameSupplyFlag;
import PublicLockAndFlag.ShortBgmLock;
import aircraft.AbstractAircraft;
import aircraft.HeroAircraft;
import application.ImageManager;
import basic.AbstractFlyingObject;
import bullet.BaseBullet;
import factory.BloodPropFactory;
import factory.BombPropFactory;
import factory.BossEnemyFactory;
import factory.EliteEnemyFactory;
import factory.EnemyFactory;
import factory.FirePropFactory;
import factory.MobEnemyFactory;
import factory.PropFactory;
import product.enemy.BossEnemy;
import product.enemy.EliteEnemy;
import product.prop.BaseProp;
import product.prop.BloodProp;
import product.prop.BombProp;
import record.ScoreBoard;
import strategy.ScatterShoot;
import strategy.StraightShoot;

public class GameViewTest extends SurfaceView implements
        SurfaceHolder.Callback,Runnable {

    //SurfaceView needed
    protected int screenWidth;
    protected int screenHeight;
    protected boolean mbLoop = false; //控制绘画线程的标志位
    protected SurfaceHolder mSurfaceHolder;
    protected Canvas canvas;  //绘图的画布
    protected Paint mPaint;


    /**
     * 时间间隔(ms)，控制刷新频率
     */
    protected int timeInterval = 40;

    protected final HeroAircraft heroAircraft;
    protected final List<AbstractAircraft> enemyAircrafts;
    protected final List<BaseBullet> heroBullets;
    protected final List<BaseBullet> enemyBullets;
    protected final List<BaseProp> props; //ver1.0添加

    protected int enemyMaxNumber = 5; //场上最多出现的敌机数量，暂定为5

    protected int mobHp = 30; //ver2.0添加，普通敌机生命值（随难度改变，目前暂定为30（一次受击））
    protected int mobSpeedY = 10*GameActivity.WINDOW_HEIGHT/500; //ver2.0添加，普通敌机纵向移速（随难度改变，目前暂定为10）

    protected float eliteOccur = 0.3f; //ver1.0添加，用于设置精英敌机出现的频率（随难度变化，目前暂定为10%）
    protected int eliteHp = 60; //ver1.0添加，用于设置精英敌机的生命值（随难度变化，目前暂定为60（两次攻击））
    protected int elitePower = 15; //ver2.0添加，精英敌机单个子弹的威力（随难度成比例改变,目前暂定为15）
    protected int eliteBulletSpeed = 10*GameActivity.WINDOW_HEIGHT/500; //ver2.0添加，精英敌机子弹速度（随难度成比例改变，越慢越难（因为慢就密集），目前暂定为12）

    protected int bossHp = 200; //ver4.0添加，用于设置boss敌机的生命值（随难度变化，目前暂定为150（五发子弹））
    protected int bossPower = 20; //ver4.0添加，boss敌机单个子弹的威力（随难度成比例改变,目前暂定为20）
    protected int bossBulletSpeed = 10*GameActivity.WINDOW_HEIGHT/500; //ver4.0添加，boss敌机子弹速度（随难度成比例改变，目前暂定为9）
    protected int bossShootNum = 3; //ver4.0添加，boss敌机子弹数量（随难度改变，数越大横向散射的子弹越多，目前暂定为5）

    protected  EnemyFactory mef = new MobEnemyFactory(mobHp,mobSpeedY); //ver2.0添加
    protected  EnemyFactory eef = new EliteEnemyFactory(eliteHp,elitePower,eliteBulletSpeed); //ver2.0添加
    protected  EnemyFactory bef = new BossEnemyFactory(bossHp, bossPower, bossBulletSpeed, bossShootNum); //ver4.0添加

    protected float propMaxNumber = 5; //ver1.0添加，用于设置当前场上最多能存在的道具数（可能根据难度而变化）
    protected float propOccur = 0.8f; //ver1.0添加，用于设置精英敌机爆出道具的频率
    protected int bloodHeal = 40;  //回血道具回复的血量值，随难度增加而增加，目前暂定为40

    protected final PropFactory blpf = new BloodPropFactory(bloodHeal); //ver2.0添加
    protected final PropFactory bopf = new BombPropFactory(); //ver2.0添加
    protected final PropFactory fpf = new FirePropFactory(); //ver2.0添加

    protected boolean gameOverFlag = false;
    protected int score = 0;
    protected int time = 0;
    protected int scoreBound = 0; //ver4.0添加，用于决定boss机出现的得分阈值
    protected boolean bossAlreadyExist = false; //ver4.0添加，用于判定场上目前是否有boss机
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    protected int cycleDuration = 600;
    protected int cycleTime = 0;

    /**
     *滚动的参数
     * **/
    protected int y1;
    protected int y2;

    private Context parentContext;
    /**
     * 火力道具生效标记
     * **/

    protected int fireActive;

    /**
     * 炸弹是否生效
     * **/
    public boolean isBomb;

    /**射速**/
    protected int heroShootSpeed;
    protected  int enemyShootSpeed;

    /**boss机阈值**/
    protected int bossScore;

    public GameViewTest(Context context, int screenWidth, int screenHeight) {

        super(context);
//        parentContext = context;
        loadImages(); //加载图片
        heroAircraft = HeroAircraft.getHeroAircraft(new StraightShoot()); //ver2.0修改，单例模式
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>(); //ver1.0添加

//        /**
//         * Scheduled 线程池，用于定时任务调度
//         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
//         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
//         */
//        this.executorService = new ScheduledThreadPoolExecutor(1);
//                ,new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        mbLoop = true;
        mPaint = new Paint();  //设置画笔
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
        this.setFocusable(true);

        //初始化标志和锁
        GameOverFlag.gameOverFlag=false;
        GameBossFlag.flag=false;
        GameHitFlag.flag=false;
        GameSupplyFlag.flag=false;
        GameBombFlag.flag=false;

        //初始化
        fireActive=0;
        isBomb=false;
        bossScore=100;
        enemyShootSpeed=1200;
        heroShootSpeed=600;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        mbLoop = false;
    }

    public int getScore() {
        return score;
    }

    public String getTime() {
        return recordedtime;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateGame(){
        time += timeInterval;

        createEnemy();

        //将射击与敌机生成解耦
        // 飞机射出子弹
        shootAction(time);

        // 子弹移动
        bulletsMoveAction();

        // 飞机移动
        aircraftsMoveAction();

        // ver1.0添加：道具移动
        propsMoveAction();

        // 撞击检测
        crashCheckAction();

        // 后处理
        postProcessAction();


        // 游戏结束检查
        if (heroAircraft.getHp() <= 0) {
            // 游戏结束
            recordTime();
            mbLoop = false;
            gameOverFlag = true;

            parentContext = this.getContext();
            Intent intent = new Intent(parentContext,RankBoard.class);
            parentContext.startActivity(intent);
//            recordTip(parentContext);
//            intent.putExtra("enteredName", enteredName);
//            parentContext.startActivity(intent);
            GameOverFlag.gameOverFlag=true;
            Log.i("updateGame","Game Over");
        }
    }

    //***********************
    //      Action 各部分
    //***********************

    protected boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    protected void shootAction(int time) {
        // TODO 敌机射击
        for (AbstractAircraft eliteEnemy : enemyAircrafts) {
            if (time%enemyShootSpeed==0){
                enemyBullets.addAll(eliteEnemy.shoot());
            }
        }
        // 英雄射击,设计周期为400ms
        if(time%heroShootSpeed==0){
            heroBullets.addAll(heroAircraft.shoot());
        }
    }

    protected void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    protected void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    /**
     * 道具移动具体实现:...
     * */
    protected void propsMoveAction() {
        for (BaseProp prop : props){
            prop.forward();
        }
    }


    /**
     * 敌机生成逻辑
     * **/

    public void createEnemy(){
        // 周期性执行（控制频率）
        if (timeCountAndNewCycleJudge()) {
            // 新敌机产生
            if(hook()){
                if (scoreBound >= bossScore && !bossAlreadyExist) { //ver4.0添加，每当在击破上一个boss机后再次得到200分以上时出现新的boss机
                    createBoss();
                }
            }
            if (enemyAircrafts.size() < enemyMaxNumber) {  //ver2.0修改过
                createMobAndElite();
            }
        }
    }

    public void createMobAndElite(){
        if (Math.random() > eliteOccur) {
            enemyAircrafts.add(mef.createEnemy());
        } else {
            enemyAircrafts.add(eef.createEnemy());
        }
    }

    public void  createBoss(){
        enemyAircrafts.add(bef.createEnemy());
        bossAlreadyExist = true;
        GameBossFlag.flag=true;
    }

    //钩子方法
    public boolean hook(){
        return false;
    }
    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    protected void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                //英雄机撞到敌机子弹损失一定生命值
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    synchronized(ShortBgmLock.lock){
                        GameHitFlag.flag=true;
                    }
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        props.add(bopf.createProp(enemyAircraft.getLocationX(),
                                enemyAircraft.getLocationY(),
                                (Math.random() > 0.5f ? 8:-8)*GameActivity.WINDOW_WIDTH/600,
                                6*GameActivity.WINDOW_HEIGHT/700)
                        );
                        // TODO 获得分数，产生道具补给
                        if (enemyAircraft instanceof EliteEnemy){  //如果是精英敌机，则击破加分更多，且有几率爆出强化道具
                            score += 50;
                            scoreBound += 50;
                            createEliteProps(enemyAircraft);
                        } else if (enemyAircraft instanceof BossEnemy) { //如果是boss敌机，加分最多，必爆三种道具
                            score += 100;
                            bossAlreadyExist = false;
                            GameBossFlag.flag=false;
                            scoreBound = 0;
                            createBossProp(enemyAircraft);

                        } else {
                            score += 10;
                            scoreBound += 10;
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for (BaseProp prop : props){
            //二次拾取道具无效
            if (prop.notValid()){
                continue;
            }
            if (heroAircraft.crash(prop)){
                synchronized(ShortBgmLock.lock){
                    GameSupplyFlag.flag=true;
                }
                if (prop instanceof BloodProp){
                    heroAircraft.setHp(heroAircraft.getHp() + ((BloodProp) prop).getBloodHeal());
                } else if (prop instanceof BombProp){
                    synchronized (ShortBgmLock.lock){
                        GameBombFlag.flag=true;
                    }
                    bombActive(prop);
                    isBomb=true;
                    System.out.println("BombSupply active!");
                } else {
                    synchronized (GameFireSupplyLock.lock1){
                        fireActive++;
                    }
                    if(fireActive<=5){
                        Log.i("Fire","Fire supply start");
                        heroAircraft.setShootWay(new ScatterShoot());
                        Runnable r=()->{
                            int lastFireActive;
                            lastFireActive=fireActive;
                            synchronized (GameFireSupplyLock.lock){
                                if(heroAircraft.getShootNum() < 3) { //最多3发子弹散射
                                    heroAircraft.setShootNum(heroAircraft.getShootNum() + 1);
                                    System.out.println("FireSupply active!");
                                } else {
                                    System.out.println("Now nobody can beat you!");
                                }
                            }
                            //道具生效10ms
                            try{
                                Thread.sleep(10000);
                            }
                            catch(Exception e){
                                e.printStackTrace();
                            }

                            //火力道具效果结束
                            if(lastFireActive==fireActive||lastFireActive==5){
                                synchronized (GameFireSupplyLock.lock1) {
                                    fireActive = 0;
                                    heroAircraft.setShootWay(new StraightShoot());
                                    heroAircraft.setShootNum(1);
                                    Log.i("Fire", "Fire supply over");
                                }
                            }
                        };
                        new Thread(r).start();
                    }
                }
                prop.vanish();
            }
        }

        if(isBomb){
            createTool();
            isBomb=false;
        }

    }


    /**
     * 炸弹生效函数
     * **/
    public void bombActive(BaseProp tool){

        //加入观察者
        tool.addObserver(enemyBullets);
        tool.addObserver(enemyAircrafts);

        //唤醒观察者
        score+=tool.notifyAllObserver();


    }

    /**
     * 炸弹生效时消灭敌机产生道具
     * **/
    public void createTool(){
        //根据观察者的状态生成道具
        for (int i=0;i<enemyAircrafts.size();i++){
            if(enemyAircrafts.get(i) instanceof EliteEnemy&&(enemyAircrafts.get(i).notValid())){
                createEliteProps(enemyAircrafts.get(i));
            }
            else if(enemyAircrafts.get(i) instanceof BossEnemy&&(enemyAircrafts.get(i).notValid())){
                createBossProp(enemyAircrafts.get(i));
                bossAlreadyExist = false;
                GameBossFlag.flag=false;
                scoreBound = 0;
            }
        }
    }

    /**
     * 道具生辰逻辑
     * **/
    public void createEliteProps(AbstractAircraft enemyAircraft){
        Random r = new Random();
        if(Math.random() < propOccur && props.size() < propMaxNumber){
            if(r.nextFloat() < 0.4f){
                props.add(blpf.createProp(enemyAircraft.getLocationX(),
                        enemyAircraft.getLocationY(),
                        (Math.random() > 0.5f ? 8:-8)*GameActivity.WINDOW_WIDTH/600,
                        6*GameActivity.WINDOW_HEIGHT/700)
                );
            } else if(r.nextFloat() < 0.8f){
                props.add(fpf.createProp(enemyAircraft.getLocationX(),
                        enemyAircraft.getLocationY(),
                        (Math.random() > 0.5f ? 8:-8)*GameActivity.WINDOW_WIDTH/600,
                        6*GameActivity.WINDOW_HEIGHT/700)
                );
            } else{
                props.add(bopf.createProp(enemyAircraft.getLocationX(),
                        enemyAircraft.getLocationY(),
                        (Math.random() > 0.5f ? 8:-8)*GameActivity.WINDOW_WIDTH/600,
                        6*GameActivity.WINDOW_HEIGHT/700)
                );
            }
        }
    }

    /**
     * 创建Boss机击毁后的道具
     * **/
    public void createBossProp(AbstractAircraft enemyAircraft){
        int x;
        int y;
        x=enemyAircraft.getLocationX();
        y=enemyAircraft.getLocationY();
        props.add(blpf.createProp(x,
                y,
                (Math.random() > 0.5f ? 8:-8)*GameActivity.WINDOW_WIDTH/600,
                6*GameActivity.WINDOW_HEIGHT/700)
        );
        props.add(fpf.createProp(x+5+(Math.random()<0.5?1:-1)*(int)(Math.random()*GameActivity.WINDOW_WIDTH/10),
                y+10+(int)(Math.random()*GameActivity.WINDOW_HEIGHT/20),
                (Math.random() > 0.5f ? 8:-8)*GameActivity.WINDOW_WIDTH/600,
                6*GameActivity.WINDOW_HEIGHT/700)
        );
        props.add(bopf.createProp(x-5+(Math.random()<0.5?1:-1)*(int)(Math.random()*GameActivity.WINDOW_WIDTH/10),
                y-10+(int)(Math.random()*GameActivity.WINDOW_HEIGHT/20),
                (Math.random() > 0.5f ? 8:-8)*GameActivity.WINDOW_WIDTH/600,
                6*GameActivity.WINDOW_HEIGHT/700)
        );
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }

    /**
     * 获取成绩达成的时间
     * */
    protected void recordTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        recordedtime = sdf.format(date);
    }

    /**
     * 适应android库的绘图方法
     * */
    public void draw(){
        //通过SurfaceHolder对象的lockCanvans()方法，我们可以获取当前的Canvas绘图对象
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        canvas = mSurfaceHolder.lockCanvas();
        if(mSurfaceHolder == null || canvas == null){
            return;
        }
        //绘制游戏界面的画面
        paint(canvas);
        //通过unlockCanvasAndPost(mCanvas)方法对画布内容进行提交
        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void run() {
        //设置一个循环来绘制，通过标志位来控制开启绘制还是停止
        while (mbLoop){
            updateGame();
            synchronized (mSurfaceHolder){
                draw();
            }
            try {
                Thread.sleep(25);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void paint(Canvas cvs){
        //滚动算法
        Log.i("Height",GameActivity.WINDOW_HEIGHT+"");
        y1=(y1>=GameActivity.WINDOW_HEIGHT)?y2:y1+GameActivity.WINDOW_HEIGHT/500;
        y2=y1-ImageManager.BACKGROUND_IMAGE.getHeight();

        cvs.drawBitmap(ImageManager.BACKGROUND_IMAGE, 0, y1, mPaint);
        cvs.drawBitmap(ImageManager.BACKGROUND_IMAGE, 0, y2, mPaint);

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(cvs, enemyBullets);
        paintImageWithPositionRevised(cvs, heroBullets);

        paintImageWithPositionRevised(cvs, enemyAircrafts);
        paintImageWithPositionRevised(cvs, props);

        cvs.drawBitmap(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, mPaint);

        //绘制得分和生命值
        paintScoreAndLife(cvs);
    }

    protected void paintImageWithPositionRevised(Canvas cvs, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (int i = 0; i < objects.size(); i++) {
            Bitmap image = objects.get(i).getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            cvs.drawBitmap(image, objects.get(i).getLocationX() - image.getWidth() / 2,
                    objects.get(i).getLocationY() - image.getHeight() / 2, mPaint);
        }
    }

    @SuppressLint("ResourceAsColor")
    protected void paintScoreAndLife(Canvas cvs) {
        int x = 10;
        int y = 100;
        mPaint.setColor(R.color.score_and_life_color);
        mPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD));
        mPaint.setTextSize(100);
        cvs.drawText("SCORE:" + this.score, x, y, mPaint);
        y = y + 100;
        cvs.drawText("LIFE:" + this.heroAircraft.getHp(), x, y, mPaint);
    }

    public void loadImages() {
        try {
            ImageManager.BACKGROUND_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
            ImageManager.HERO_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.hero);
            ImageManager.MOB_ENEMY_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.mob);
            ImageManager.ELITE_ENEMY_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.elite);
            ImageManager.BOSS_ENEMY_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.boss);
            ImageManager.HERO_BULLET_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.bullet_hero);
            ImageManager.ENEMY_BULLET_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.bullet_enemy);
            ImageManager.PROP_BLOOD_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.prop_blood);
            ImageManager.PROP_BOMB_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.prop_bomb);
            ImageManager.PROP_BULLET_IMAGE = BitmapFactory.decodeResource(getResources(), R.drawable.prop_bullet);

            ImageManager.setUpClassnameImageMap();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}