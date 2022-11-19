package model;

import movement.util.Coordinate;
import movement.util.Vector;

public class Asteroid implements Collideable<Asteroid>, Movable<Asteroid>{

    private final String id;
    private final int size;
    private final Coordinate position;
    private final Vector vector;

    public Asteroid(String id, int size, Coordinate position, Vector vector) {
        this.id = id;
        this.size = size;
        this.position = position;
        this.vector = vector;
    }

    @Override
    public Asteroid collide(Collideable other) {
        return new Asteroid(id, size - other.getDamage(), position, vector);
    }

    @Override
    public int getHealth() {
        return size;
    }

    @Override
    public int getDamage() {
        return size;
    }

    @Override
    public Asteroid move(double time) {
        return null;
    }

    @Override
    public Asteroid rotate(double degrees) {
        return new Asteroid(id, size, position, vector.rotate(degrees));
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Coordinate getPosition() {
        return position;
    }

    @Override
    public Vector getVector() {
        return vector;
    }

    @Override
    public double getRotationInDegrees() {
        return vector.getAngleInDegrees();
    }

    @Override
    public double getSpeed() {
        return 1;
    }
}
