package factory;

import javafx.scene.input.KeyCode;
import movement.KeyMovement;

import java.util.List;
import java.util.Map;

public class ConfigFactory {

    public static Map<String, Map<KeyMovement,KeyCode>> createShipDefaultConfig(){
        return Map.of(
                "starship-0", Map.of(
                        KeyMovement.ACCELERATE, KeyCode.W,
                        KeyMovement.TURN_LEFT, KeyCode.A,
                        KeyMovement.TURN_RIGHT, KeyCode.D,
                        KeyMovement.STOP, KeyCode.S,
                        KeyMovement.SHOOT, KeyCode.SPACE
                ),
                "starship-1", Map.of(
                        KeyMovement.ACCELERATE, KeyCode.UP,
                        KeyMovement.TURN_LEFT, KeyCode.LEFT,
                        KeyMovement.TURN_RIGHT, KeyCode.RIGHT,
                        KeyMovement.STOP, KeyCode.DOWN,
                        KeyMovement.SHOOT, KeyCode.L
                )
        );
    }
}
