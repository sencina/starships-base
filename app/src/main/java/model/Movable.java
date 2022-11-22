package model;

import movement.util.Position;
import movement.util.Vector;

public interface Movable extends Entity {

    Movable move(double time);

    Movable rotate(double degrees);
    Position getPosition();
    Vector getVector();
    double getRotationInDegrees();

    double getSpeed();

    Movable stop();
}
