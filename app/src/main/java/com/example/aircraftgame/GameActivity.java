package com.example.aircraftgame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.aircraftgame.GameDifficulty.DifficultGameView;
import com.example.aircraftgame.GameDifficulty.EasyGameView;
import com.example.aircraftgame.GameDifficulty.NormalGameView;
import com.example.aircraftgame.NetGame.NetGame;
import com.example.aircraftgame.NetGame.PlayerInfo;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import MusicPlayer.MusicServer;
import PublicLockAndFlag.GameOverFlag;
import PublicLockAndFlag.OnlineGameOver;
import aircraft.HeroAircraft;
import application.ImageManager;
import sp_objects.Laser;
import sp_objects.LaserEffect;
import sp_objects.Shield;
import strategy.StraightShoot;

public class GameActivity extends AppCompatActivity {
    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;

    private static GameViewTest gvt;
    //检查游戏是否需要音效
    private static boolean GameNeedVideo;
    //获取选择的游戏难度
    private static int GameDifficulty;
    private final String OnlineTAG="OnlineGame";
    //网络通信
    public static Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    //handler
    private Handler mHandler;
    //点击次数标记，处理点击抖动
    private static int touchTime;

    //修改图片
    ImageView imageView1;
    private boolean imageFlag;
    ImageView imageView2;
    @SuppressLint({"ResourceType", "UseCompatLoadingForDrawables"})
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //检查 游戏的难度 和 游戏是否需要音效，设置 GameDifficulty 和 GameNeedVideo 两个变量
        Intent neededData = getIntent();
        GameNeedVideo = neededData.getBooleanExtra("videoIsNeeded", false);
        GameDifficulty = neededData.getIntExtra("difficulty", 1);
        Log.i("Game",GameNeedVideo+"");
        Log.i("Game",GameDifficulty+"");

        //隐藏标题全屏游玩
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindowSize();
        setContentView(R.layout.activity_main);
        imageFlag=true;
        //Handler实例化
        mHandler=new mHandler();

        if(StartActivity.getIsOnline()){
            new OnlineGame().start();
        }
        else{
            localGame();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(!isBonusPropTouched(event)) {
            HeroAircraft.getHeroAircraft(new StraightShoot()).setLocation(event.getX(),
                    event.getY() - ImageManager.HERO_IMAGE.getHeight() / 2);
            Shield.getShield(event.getX(),
                    event.getY() - ImageManager.HERO_IMAGE.getHeight() / 2);
            Laser.getLaser(event.getX(), event.getY() - 200);
            LaserEffect.getLaserEffect(event.getX(), event.getY() - 200);
        }
        return true;
    }

    /**
     * 检查用户是否点击道具商城图标
     * */
    public boolean isBonusPropTouched(MotionEvent event){
        int x = (int)event.getX();
        int y = (int)event.getY();
        Log.i("Touched: x = ", "" + x);
        Log.i("Touched: y = ", "" + y);
        if(y >= 2200 && y <= 2300) {
            if (x >= 10 && x <= 130) {
                touchTime++;
                GameViewTest.setBonusSelected(0);
                return true;
            } else if(x >= 210 && x <= 330) {
                touchTime++;
                GameViewTest.setBonusSelected(1);
                return true;
            } else if(x >= 410 && x <= 530) {
                touchTime++;
                GameViewTest.setBonusSelected(2);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void getWindowSize(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        WINDOW_WIDTH = dm.widthPixels;
        WINDOW_HEIGHT = dm.heightPixels;
    }

    public static GameViewTest getGvt() {
        return gvt;
    }

    /**
    * 调用该静态方法判断游戏是否需要音效
    **/
    public static boolean isGameNeedVideo() {
        return GameNeedVideo;
    }

    /**
    * 调用该静态方法判断本局游戏选择的难度，简单、普通、困难分别对应数字1、2、3
    **/
    public static int getGameDifficulty() {
        return GameDifficulty;
    }

    /**
     * 处理联网游戏的逻辑
     * **/
    public class OnlineGame extends Thread{

      @Override
        public void run(){
          try{
              //往Handler里面发送消息
              Message msg=Message.obtain();
              Log.i(OnlineTAG,"发送连接请求");
              socket=new Socket();
              socket.connect(new InetSocketAddress("192.168.56.1",1111),5000);
              Log.i(OnlineTAG,"玩家连接完毕");
              output =new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
              input=new BufferedReader(new InputStreamReader(socket.getInputStream()));
              Log.i(OnlineTAG,"等待另一个玩家连接");
            if(input.readLine().equals("true")){
                imageFlag=false;
                gvt=new NetGame(GameActivity.this,WINDOW_WIDTH,WINDOW_HEIGHT);
                msg.what=2;
                msg.obj="Game";
                mHandler.sendMessage(msg);
                startMusic();
                Log.i(OnlineTAG,"网络游戏开始");
                new Client().start();
            }

          }catch(Exception e){
              e.printStackTrace();
          }
      }
    }

    /**本地游戏**/
    public void localGame(){
                switch(GameDifficulty){
            case 1:
                Log.i("Game","简单模式");
                gvt = new EasyGameView(this, WINDOW_WIDTH, WINDOW_HEIGHT);
                break;
            case 2:
                Log.i("Game","普通模式");
                gvt = new NormalGameView(this, WINDOW_WIDTH, WINDOW_HEIGHT);
                break;
            case 3:
                Log.i("Game","困难模式");
                gvt = new DifficultGameView(this, WINDOW_WIDTH, WINDOW_HEIGHT);
                break;
            default:
                Log.e("Game","选择错误");

        }

        setContentView(gvt);
        startMusic();
    }

    /**音乐播放**/
    public void startMusic(){
        //设置service播放音乐
        //新建一条线程来执行
        if(GameNeedVideo){
            Log.i("Game","播放音乐");
            Intent intent=new Intent(this, MusicServer.class);
            new Thread(()->{
                while(!GameOverFlag.gameOverFlag){
                    startService(intent);
                }
                stopService(intent);
            }).start();
        }
    }

    /**网络服务线程**/
    class Client extends Thread{
        @Override
        public void run(){
            //数据交换
                try{
                    String content;
                    //在双方游戏结束前时刻监听信号

                    //发送初始化请求
                    output.println(PlayerInfo.playerInfo);
                    output.flush();
                    imageFlag=true;
                    while((content= input.readLine())!=null){
                        PlayerInfo.playerInfo=new JSONObject(content);
                        Log.i(OnlineTAG,"Player Info: "+PlayerInfo.playerInfo+"");
                        //游戏未结束，持续输送信号
                        if(GameOverFlag.gameOverFlag){
                            PlayerInfo.playerInfo.put("GameOver",true);
                            Log.i(OnlineTAG,"Waiting the other player over");

                            //切换到等待页面
                            Message msg=Message.obtain();
                            msg.what=3;
                            msg.obj="waiting opponent Over";
                            mHandler.sendMessage(msg);
                        }
                        output.println(PlayerInfo.playerInfo);
                        output.flush();
                    }
                    //设置一个双方游戏结束标志
                    //TODO
                    Log.i(OnlineTAG,"Disconnect Socket,game over");
                    PlayerInfo.playerInfo.put("OnlineDisconnect",true);
                    OnlineGameOver.flag=true;
                    jumpToBattleResult();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
        }
    }

    /**Handler类更新UI**/
    @SuppressLint("HandlerLeak")
    class mHandler extends Handler{
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                //等待链接页面
                case 1:
                    Log.i(OnlineTAG,"等待对手开始游戏");
                    setContentView(R.layout.activity_main);
                    break;
                //游戏进行页面
                case 2:
                    setContentView(gvt);
                    break;

                //等待游戏结束页面
                case 3:
                    //TODO
                    Log.i(OnlineTAG,"等待对手结束游戏");
                    setContentView(R.layout.activity_waiting);
                    break;
                default:
                    Log.i(OnlineTAG,"页面错误");
                    setContentView(R.layout.activity_main);

                    break;
            }
        }
    }
     /* 外部获取点击次数的标志
     * */
    public static int getTouchTime() {
        return touchTime;
    }

    /**外部重置点击次数标志的值*/
    public static void setTouchTime(int myTouchTime){
        if(myTouchTime != 0){
            return;
        } else {
            touchTime = myTouchTime;
        }
    }

    /**修改等待页面的背景图**/
    public void changeBackground(ImageView imageView){
        int[] image=new int[]{R.drawable.wait1,R.drawable.wait2,R.drawable.wait3
        ,R.drawable.wait4,R.drawable.wait5};
        int count=0;
        //轮播图
        while(imageFlag){
            try{
                Thread.sleep(2000);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        imageView.setImageResource(R.drawable.wait2);
        Log.i("Loop","切换图片");
        }
        }
    /**
     * 对战结束跳转到结果界面
     * */
    public void jumpToBattleResult(){
        Intent intent = new Intent(GameActivity.this, OnlineBattleResult.class);
        startActivity(intent);
//        try {
//            if (PlayerInfo.playerInfo.getInt("Score") > PlayerInfo.playerInfo.getInt("Score2")){
//                Intent intent = new Intent(this,)
//            }
//        } catch(Exception e){
//            e.printStackTrace();
//        }
    }
}