package model;

import movement.util.Coordinate;
import movement.util.Vector;

public interface Movable<T> extends Entity {

    T move(double time);

    T rotate(double degrees);
    Coordinate getPosition();
    Vector getVector();
    double getRotationInDegrees();

    double getSpeed();
}
