package model;

import movement.Position;

public interface Movable extends Entity {

    Movable move();

    Movable rotate(double degrees);
    Position getPosition();
    double getVector();
    double getRotationInDegrees();

    double getSpeed();

    Movable stop();

    Movable accelerate();
}
