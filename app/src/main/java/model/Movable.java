package model;

import movement.Mover;
import movement.util.Coordinate;
import movement.util.Vector;

public interface Movable extends Entity {

    Movable move(double time);

    Movable rotate(double degrees);
    Coordinate getPosition();
    Vector getVector();
    double getRotationInDegrees();

    double getSpeed();

    Movable stop();
}
