package Task3;


import java.util.ArrayList;
import java.util.List;

public class Cardindex {
    public static void main(String[] args) {

        List<Movie> films = new ArrayList<>();

        films.add(new Movie("Terminator", 8, "fantastic", "USA", false));
        films.add(new Movie("Green Mile", 9, "drama", "USA", true));
        films.add(new Movie("Love and pigeons", 7, "drama", "Russia", true));

        System.out.println(films.get(1));




    }
}
