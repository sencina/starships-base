import javafx.scene.input.KeyCode;
import model.KeyAction;
import state.KeyMapper;
import movement.KeyMovement;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class KeyMapperTest {

        @Test
        public void testGetAction() {
                KeyMapper keyMapper = new KeyMapper(Map.of("test", List.of(new KeyAction(KeyCode.A, KeyMovement.TURN_LEFT))));
                assertEquals(new KeyAction(KeyCode.A, KeyMovement.TURN_LEFT), keyMapper.getAction("test", KeyCode.A));
        }

        @Test
        public void testSetAction() {
                KeyMapper keyMapper = new KeyMapper(Map.of("test", List.of(new KeyAction(KeyCode.A, KeyMovement.TURN_LEFT))));
                KeyMapper updatedKeyMapper = keyMapper.setAction("test", new KeyAction(KeyCode.A, KeyMovement.TURN_RIGHT));
                assertEquals(KeyMovement.TURN_RIGHT, updatedKeyMapper.getAction("test", KeyCode.A).action());
        }
}
