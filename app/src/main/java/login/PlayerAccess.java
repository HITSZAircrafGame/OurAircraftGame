package login;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.aircraftgame.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class PlayerAccess implements PlayerAccountDao {

    private static final String TAG = "PlayerAccess";

    @SuppressLint("LongLogTag")
    @Override
    public int login(String playerName, String password){
        HashMap<String, Object> map = new HashMap<>();
        Connection connection = JDBCUtils.getConn();
        int msg = 0;
        try{
            String sql = "select * from player_registered where playerName = ?";
            if (connection != null){
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null){
                    Log.e(TAG,"账号: " + playerName);
                    ps.setString(1, playerName);
                    ResultSet rs = ps.executeQuery();
                    int count = rs.getMetaData().getColumnCount();
                    while (rs.next()){
                        for (int i = 1; i <= count; i++){
                            String field = rs.getMetaData().getColumnName(i);
                            map.put(field, rs.getString(field));
                        }
                    }
                    connection.close();
                    ps.close();
                    if (map.size() != 0){
                        StringBuilder s = new StringBuilder();
                        for (String key : map.keySet()){
                            if (key.equals("password")){
                                if (password.equals(map.get(key))){
                                    //表示密码正确，会显示登陆成功信息
                                    msg = 1;
                                }
                                else{
                                    //表示密码错误，会显示密码错误请重新输入的信息
                                    msg = 2;
                                }
                                break;
                            }
                        }
                    } else {
                        Log.e(TAG, "查询到的表为空");
                        //表示玩家尚未注册，提示玩家先注册
                        msg = 3;
                    }
                } else {
                    msg = 0;
                }
            } else {
                msg = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG,"登陆异常: " + e.getMessage());
            msg = 0;
        }
        return msg;
    }

    @SuppressLint("LongLogTag")
    @Override
    public boolean register(Player player){
        HashMap<String, Object> map = new HashMap<>();
        Connection connection = JDBCUtils.getConn();

        try{
            String sql = "insert into " +
                    "player_registered(playerName,password) values (?,?)";
            if (connection != null) {
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null) {
                    ps.setString(1, player.getPlayerName());
                    ps.setString(2, player.getPassword());
                    int rs = ps.executeUpdate();
                    if (rs > 0) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "注册异常: " + e.getMessage());
            return false;
        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public Player findPlayer(String playerName){
        Connection connection = JDBCUtils.getConn();
        Player player = null;
        try {
            String sql = "select * from player_registered where playerName = ?";
            if (connection != null) {
                PreparedStatement ps = connection.prepareStatement(sql);
                if (ps != null) {
                    ps.setString(1, playerName);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        int idGet = rs.getInt(1);
                        String playerNameGet = rs.getString(2);
                        String passwordGet = rs.getString(3);
                        int bounsGet = rs.getInt(4);
                        player = new Player(playerNameGet, passwordGet, bounsGet);
                        player.setId(idGet);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "查询异常: " + e.getMessage());
            return null;
        }
        return player;
    }
}










