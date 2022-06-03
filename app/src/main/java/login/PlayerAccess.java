package login;

import android.content.ContentValues;
import android.util.Log;

import androidx.versionedparcelable.ParcelField;

import org.litepal.LitePal;
import java.util.LinkedList;
import java.util.List;

public class PlayerAccess implements PlayerAccountDao {

    private List<Player> players;

    public PlayerAccess(){
        players = new LinkedList<>();
        if (LitePal.getDatabase() != null){
            players =  LitePal.findAll(Player.class);
        }
    }

    @Override
    public void addPlayer(String playerName, String password){
        Player player = new Player(playerName, password, 0);
        if (player.save()){
            Log.d("Add player","succeed!");
        } else {
            Log.d("Add player", "fail!");
        }
    }

    @Override
    public void updatePlayer(String playerName, int bonus){
        ContentValues cv = new ContentValues();
        cv.put("bonus",bonus);
        LitePal.updateAll(Player.class, cv,"playerName=?",playerName);
    }

    @Override
    public void deletePlayer(String playerName){
        LitePal.deleteAll(Player.class, "playerName=?", playerName);
    }

    @Override
    public Player findPlayer(String playerName){
        Player player = null;
        for (int i = 0; i < players.size(); i++){
            if (playerName.equals(players.get(i).getPlayerName())){
                player = players.get(i);
                break;
            }
        }
        return player;
    }
}
//    @SuppressLint("LongLogTag")
//    @Override
//    public int login(String playerName, String password){
//        HashMap<String, Object> map = new HashMap<>();
//        Connection connection = JDBCUtils.getConn();
//        int msg = 0;
//        try{
//            String sql = "select * from player_registered where playerName = ?";
//            if (connection != null){
//                PreparedStatement ps = connection.prepareStatement(sql);
//                if (ps != null){
//                    Log.e(TAG,"账号: " + playerName);
//                    ps.setString(1, playerName);
//                    ResultSet rs = ps.executeQuery();
//                    int count = rs.getMetaData().getColumnCount();
//                    while (rs.next()){
//                        for (int i = 1; i <= count; i++){
//                            String field = rs.getMetaData().getColumnName(i);
//                            map.put(field, rs.getString(field));
//                        }
//                    }
//                    connection.close();
//                    ps.close();
//                    if (map.size() != 0){
//                        StringBuilder s = new StringBuilder();
//                        for (String key : map.keySet()){
//                            if (key.equals("password")){
//                                if (password.equals(map.get(key))){
//                                    //表示密码正确，会显示登陆成功信息
//                                    msg = 1;
//                                }
//                                else{
//                                    //表示密码错误，会显示密码错误请重新输入的信息
//                                    msg = 2;
//                                }
//                                break;
//                            }
//                        }
//                    } else {
//                        Log.e(TAG, "查询到的表为空");
//                        //表示玩家尚未注册，提示玩家先注册
//                        msg = 3;
//                    }
//                } else {
//                    failFlag = 1;
//                    msg = 0;
//                }
//            } else {
//                failFlag = 2;
//                msg = 0;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d(TAG,"登陆异常: " + e.getMessage());
//            failFlag = 3;
//            msg = 0;
//        }
//        return msg;
//    }
//
//    @SuppressLint("LongLogTag")
//    @Override
//    public boolean register(Player player){
//        HashMap<String, Object> map = new HashMap<>();
//        Connection connection = JDBCUtils.getConn();
//
//        try{
//            String sql = "insert into " +
//                    "player_registered(playerName,password) values (?,?)";
//            if (connection != null) {
//                PreparedStatement ps = connection.prepareStatement(sql);
//                if (ps != null) {
//                    ps.setString(1, player.getPlayerName());
//                    ps.setString(2, player.getPassword());
//                    int rs = ps.executeUpdate();
//                    if (rs > 0) {
//                        return true;
//                    } else {
//                        return false;
//                    }
//                } else {
//                    return false;
//                }
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e(TAG, "注册异常: " + e.getMessage());
//            return false;
//        }
//    }
//
//    @SuppressLint("LongLogTag")
//    @Override
//    public Player findPlayer(String playerName){
//        Connection connection = JDBCUtils.getConn();
//        Player player = null;
//        try {
//            String sql = "select * from player_registered where playerName = ?";
//            if (connection != null) {
//                PreparedStatement ps = connection.prepareStatement(sql);
//                if (ps != null) {
//                    ps.setString(1, playerName);
//                    ResultSet rs = ps.executeQuery();
//
//                    while (rs.next()) {
//                        int idGet = rs.getInt(1);
//                        String playerNameGet = rs.getString(2);
//                        String passwordGet = rs.getString(3);
//                        int bounsGet = rs.getInt(4);
//                        player = new Player(playerNameGet, passwordGet, bounsGet);
//                        player.setId(idGet);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d(TAG, "查询异常: " + e.getMessage());
//            return null;
//        }
//        return player;
//    }










