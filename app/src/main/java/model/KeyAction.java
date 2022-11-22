package model;

import javafx.scene.input.KeyCode;
import movement.KeyMovement;

public record KeyAction(KeyCode key, KeyMovement action) {

}
