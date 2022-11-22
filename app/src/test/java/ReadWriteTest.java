import config.reader.ConfigManager;
import enums.BulletType;
import factory.ConfigFactory;
import factory.EntityFactory;
import model.KeyAction;
import movement.util.Position;
import movement.util.Vector;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;
import state.GameState;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ReadWriteTest {

    @Test
    public void testWrite() throws IOException, ParseException {
        ConfigManager configManager = new ConfigManager("/Users/sencina/faculty/starships-base/app/src/main/java/config/keys.json");
        configManager.saveKeys(ConfigFactory.createShipDefaultConfig());
    }

    @Test
    public void testRead() throws IOException, ParseException {
        ConfigManager configManager = new ConfigManager("/Users/sencina/faculty/starships-base/app/src/main/java/config/keys.json");
        Map<String, List<KeyAction>> map = configManager.loadKeys();
        Assert.assertEquals(1, map.size());
    }

    @Test
    public void writeState() throws IOException {
        ConfigManager configManager = new ConfigManager("/Users/sencina/faculty/starships-base/app/src/main/java/config/state.json");
        configManager.saveState(new GameState(800, 800, List.of(EntityFactory.createBulletMover(BulletType.BULLET, new Position(0, 0), new Vector(1, 1), "starship-0")), List.of(EntityFactory.createDefaultShipControllerForTesting())));
    }

    @Test
    public void readEntities() throws IOException, ParseException {
        ConfigManager configManager = new ConfigManager("/Users/sencina/faculty/starships-base/app/src/main/java/config/state.json");
        GameState state = configManager.readState();
        Assert.assertEquals(1, state.getEntities().size());
        Assert.assertEquals(1, state.getShipControllers().size());
    }
}
