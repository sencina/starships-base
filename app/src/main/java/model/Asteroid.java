package model;

import movement.util.Coordinate;
import movement.util.Vector;

import java.util.Optional;

public class Asteroid implements Collideable<Asteroid>{

    private final String id;
    private final int size;

    public Asteroid(String id, int size) {
        this.id = id;
        this.size = size;
    }

    @Override
    public Optional<Asteroid> collide(Collideable other) {

        if (size - other.getDamage() <= 0) {
            return Optional.empty();
        }
        return Optional.of(new Asteroid(id, size - other.getDamage()));
    }

    @Override
    public int getLives() {
        return size;
    }

    @Override
    public int getDamage() {
        return size;
    }

    @Override
    public String getId() {
        return id;
    }
}
