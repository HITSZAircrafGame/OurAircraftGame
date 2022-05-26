package login;

//Player类是一个JavaBean

public class Player {
    /**
     * 玩家账号的信息：玩家名称、账号密码、积分
     * */
    private int id;
    private String playerName;
    private String password;
    private int bonus;

    public Player(String playerName, String password, int bonus){
        this.playerName = playerName;
        this.password = password;
        this.bonus = bonus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
}