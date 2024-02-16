package Task1;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storage {
    public static void main(String[] args) {

        List<Player> playerList = new ArrayList<>();

        Map<Integer, Player> toId = new HashMap<>();

        toId.put(101, new Player(101, "Taddy", true));
        toId.put(102, new Player(102, "Tarantino", false));
        toId.put(103, new Player(103, "Ron", false));
        toId.put(104, new Player(104, "Alexey", true));
        toId.put(105, new Player(105, "Billy", true));
        toId.put(106, new Player(106, "Anton", true));
        toId.put(107, new Player(107, "Helga", false));
        toId.put(108, new Player(108, "Maria", true));
        toId.put(109, new Player(109, "Natan", false));
        toId.put(102, new Player(102, "Tarantino", false));

        System.out.println(toId.get(102));

// может с точки зрения алгоритма защиты от дубляжа я ошибся, но действовал из такой логики:
// в базе могут быть тезки, но каждому отдельному человеку дается уникальный айди.
// этот айди и является ключом, а значения - его имя и состояние.
// на этом основании исключаетя дубляж.











    }
}
