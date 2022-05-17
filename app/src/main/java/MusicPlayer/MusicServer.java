package MusicPlayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.example.aircraftgame.R;

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
    private MediaPlayer hitPlayer;
    private MediaPlayer bombPlayer;
    private MediaPlayer supplyPlayer;
    private MediaPlayer gameOverPlayer;
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
                hitPlayer=MediaPlayer.create(this,R.raw.bullet_hit);
                hitPlayer.start();
                GameHitFlag.flag=false;
            }
            if(GameBombFlag.flag){
                bombPlayer=MediaPlayer.create(this,R.raw.bomb_explosion);
                bombPlayer.start();
                GameBombFlag.flag=false;
            }
            if(GameSupplyFlag.flag){
                supplyPlayer=MediaPlayer.create(this,R.raw.get_supply);
                supplyPlayer.start();
                GameSupplyFlag.flag=false;
            }
        }
        if(GameOverFlag.gameOverFlag){
            gameOverPlayer=MediaPlayer.create(this,R.raw.game_over);
            gameOverPlayer.start();
        }
    }
    @Override
    public void onCreate(){
        super.onCreate();
        Log.i(TAG,"Create MusicService");
        //初始化
        normalPlayer=MediaPlayer.create(this,R.raw.bgm);
        bossPlayer=MediaPlayer.create(this,R.raw.bgm_boss);
    }

}