package HMWRK7.data;

import HMWRK7.Player;

import java.io.IOException;
import java.util.Collection;

public interface DataProvider {

    void save (Collection<Player> players) throws IOException;

    Collection<Player> load ();







}


