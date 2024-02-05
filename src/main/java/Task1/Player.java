package Task1;

import java.util.Objects;

public class Player {
    private String nickName;
    private boolean IsOnline;
    private int id;





    public Player(int id, String nickName, boolean isOnline) {
        this.id = id;
        this.nickName = nickName;
        this.IsOnline = isOnline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return IsOnline == player.IsOnline && id == player.id && Objects.equals(nickName, player.nickName);
    }

    @Override
    public String toString() {
        return "Player{" +
                "nickName='" + nickName + '\'' +
                ", IsOnline=" + IsOnline +
                ", id=" + id +
                '}';
    }
}
