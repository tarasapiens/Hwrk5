package HMWRK7.data;

import HMWRK7.Player;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

public class DataProvederJSON implements DataProvider{

    private ObjectMapper mapper = new ObjectMapper();




    @Override
    public void save(Collection<Player> players) throws IOException {
        mapper.writeValue(Path.of("C:\\Users\\Inkvizitor\\IdeaProjects\\Hwrk5\\src\\test\\data.json").toFile(), players);

            }

    @Override
    public Collection<Player> load() {
        return null;
    }
}
