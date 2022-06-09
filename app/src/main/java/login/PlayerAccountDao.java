package login;

public interface PlayerAccountDao {
    void addPlayer(String playerName, String password);
    void updatePlayer(String playerName,int bonus);
    void deletePlayer(String playerName);
    Player findPlayer(String playerName);
}
