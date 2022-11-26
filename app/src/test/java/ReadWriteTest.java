import config.manager.ConfigManager;
import factory.ConfigFactory;
import factory.StateFactory;
import javafx.scene.input.KeyCode;
import movement.KeyMovement;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;
import state.GameState;

import java.io.IOException;
import java.util.Map;

public class ReadWriteTest {
    @Test
    public void writeState() throws IOException {
        ConfigManager.saveState(StateFactory.createTestGameState());
    }

    @Test
    public void readEntities() throws IOException, ParseException {
        GameState state = ConfigManager.readState();
        Assert.assertEquals(1, state.getEntities().size());
        Assert.assertEquals(1, state.getShipControllers().size());
    }

    @Test
    public void writeKeys() throws IOException {
        ConfigManager.saveKeys(ConfigFactory.createShipDefaultConfig());
    }

    @Test
    public void readKeys() throws IOException, ParseException {
        Map<String, Map<KeyMovement, KeyCode>> map = ConfigManager.readBindings();
        Assert.assertEquals(2, map.size());
    }
}
