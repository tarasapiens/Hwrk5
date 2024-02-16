package HMWRK7;

import HMWRK7.data.DataProvederJSON;
import HMWRK7.data.DataProvider;

import java.util.*;

public class PlayerServiceJSON implements PlayerService {

    private Map<Integer, Player> players;
    private int enumerator;
    private Set<String> nicknames;

    private DataProvider provider;

    public PlayerServiceJSON() {

       provider = new DataProvederJSON();
       Collection<Player> currentList = provider.load();
       int maxId = 0;
       players = new HashMap<>();
       nicknames = new HashSet<>();
       for (Player player : currentList) {
           players.put(player.getId(), player);
           nicknames.add(player.getNick());
           if (player.getId() > maxId){
               maxId = player.getId();
           }
       }
       enumerator = maxId;


    }
    private void saveToFile() {
        try {
            this.provider.save(players.values());
        } catch (Exception ex){
            System.out.println("не удалось сохранить");
        }

    }
    @Override
    public Player getPlayerById(int id) {
        return this.players.get(id);
    }

    @Override
    public Collection<Player> getPlayers() {
        return this.players.values();
    }

    @Override
    public int createPlayer(String nickname) {
        if (nicknames.contains(nickname)){
            throw new IllegalArgumentException("Ник уже используется" + nickname);
        }
        enumerator++;
        Player player = new Player(enumerator, nickname, 0,true);
        this.players.put(player.getId(), player);
        this.nicknames.add(nickname);
        saveToFile();
        return player.getId();
    }

    @Override
    public Player deletePlayer(int id) {
        Player z = this.players.remove(id);
        saveToFile();
        return z;
    }

    @Override
    public int addPoints(int playerId, int points) {
        Player player = this.players.get(playerId);
        int enumeratorPoints = player.getPoints();
        int newPoints = enumeratorPoints + points;
        player.setPoints(newPoints);
        saveToFile();
        return player.getPoints();
    }


}
