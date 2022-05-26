package login;

public interface PlayerAccountDao {
    int login(String playerName, String password);
    boolean register(Player player);
    Player findPlayer(String playerName);
}
