package MusicPlayer;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.IBinder;
import android.util.Log;

import com.example.aircraftgame.R;

import java.util.HashMap;
import java.util.Map;

import PublicLockAndFlag.GameBombFlag;
import PublicLockAndFlag.GameBossFlag;
import PublicLockAndFlag.GameHitFlag;
import PublicLockAndFlag.GameOverFlag;
import PublicLockAndFlag.GameSupplyFlag;
import PublicLockAndFlag.ShortBgmLock;

public class MusicServer extends Service {
    private static final String TAG="MusicService";
    private MediaPlayer normalPlayer;
    private MediaPlayer bossPlayer;
    private MediaPlayer bombPlayer;
    private MediaPlayer supplyPlayer;
    private MediaPlayer gameOverPlayer;
    private SoundPool soundPool;
    private Map<Integer,Integer> musicMap;
    /**Music open**/
    public MusicServer() {
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        if(!GameBossFlag.flag){
            if(bossPlayer.isPlaying()){
                stopMusic(bossPlayer);
            }
            if(!normalPlayer.isPlaying()){
                Log.i(TAG,"Normal BGM playing");
                normalPlayer=MediaPlayer.create(this,R.raw.bgm);
                normalPlayer.setVolume(1,1);
                normalPlayer.setLooping(true);
                normalPlayer.start();
            }
        }
        else{
            if(normalPlayer.isPlaying()){
                stopMusic(normalPlayer);
            }
            if(!bossPlayer.isPlaying()){
                Log.i(TAG,"Boss BGM playing");
                bossPlayer=MediaPlayer.create(this,R.raw.bgm_boss);
                bossPlayer.setVolume(1,1);
                bossPlayer.setLooping(true);
                bossPlayer.start();
            }
        }
        playShortBgm();
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        stopAllMusic();
        Log.i(TAG,"MusicService is destroyed");
    }

    /**
     * 停止播放
     * **/
    public void stopMusic(MediaPlayer player){
        player.stop();
        player.reset();
        try{
            player.prepare();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stopAllMusic(){
        stopPlayer(normalPlayer);
        stopPlayer(bossPlayer);
    }

    public void stopPlayer(MediaPlayer player){
        player.stop();
        player.reset();
        player.release();
    }

    public void playShortBgm(){
        synchronized (ShortBgmLock.lock){
            if(GameHitFlag.flag){
                soundPool.play(musicMap.get(2),1,1,0,0,1);
                GameHitFlag.flag=false;
            }
            if(GameBombFlag.flag){
//                bombPlayer=MediaPlayer.create(this,R.raw.bomb_explosion);
//                bombPlayer.start();
                soundPool.play(musicMap.get(1),1,1,0,0,1);
                GameBombFlag.flag=false;
            }
            if(GameSupplyFlag.flag){
//                supplyPlayer=MediaPlayer.create(this,R.raw.get_supply);
//                supplyPlayer.start();
                soundPool.play(musicMap.get(4),1,1,0,0,1);
                GameSupplyFlag.flag=false;
            }
        }
        if(GameOverFlag.gameOverFlag){
//            gameOverPlayer=MediaPlayer.create(this,R.raw.game_over);
//            gameOverPlayer.start();
            soundPool.play(musicMap.get(3),1,1,0,0,1);
        }
    }
    @Override
    public void onCreate(){
        super.onCreate();
        Log.i(TAG,"Create MusicService");
        //初始化
        normalPlayer=MediaPlayer.create(this,R.raw.bgm);
        normalPlayer.setVolume(1,1);
        bossPlayer=MediaPlayer.create(this,R.raw.bgm_boss);
        bossPlayer.setVolume(1,1);
        //设置音效池的属性
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                //设置音效使用场景
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        //初始化soundPool播放特效音乐
        soundPool=new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .setMaxStreams(6)
                .build();
        musicMap=new HashMap<>();
        musicMap.put(1,soundPool.load(this,R.raw.bomb_explosion,1));
        musicMap.put(2,soundPool.load(this,R.raw.bullet_hit,1));
        musicMap.put(3,soundPool.load(this,R.raw.game_over,1));
        musicMap.put(4,soundPool.load(this,R.raw.get_supply,1));
    }

}