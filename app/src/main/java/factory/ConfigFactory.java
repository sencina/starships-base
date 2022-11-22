package factory;

import javafx.scene.input.KeyCode;
import model.KeyAction;
import movement.KeyMovement;

import java.util.List;
import java.util.Map;

public class ConfigFactory {

    public static Map<String, List<KeyAction>> createShipDefaultConfig(){
        return Map.of(
                "starship-0", List.of(
                        new KeyAction(KeyCode.A, KeyMovement.TURN_LEFT),
                        new KeyAction(KeyCode.D, KeyMovement.TURN_RIGHT),
                        new KeyAction(KeyCode.W, KeyMovement.ACCELERATE),
                        new KeyAction(KeyCode.S, KeyMovement.STOP),
                        new KeyAction(KeyCode.SPACE, KeyMovement.SHOOT)
                )
        );
    }
}
