package Task1;

public class Playersbase {

    public static void main(String[] args) {

        Player player1 = new Player(88, "Donny", true);
        Player player2 = new Player(88, "Donny", true);

        System.out.println(player1 == player2);
        System.out.println(player1.equals(player2));

            }
}
