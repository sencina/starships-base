package model;

import movement.util.Coordinate;
import movement.util.Vector;

import java.util.Optional;

public class Bullet implements Collideable<Bullet>{

    private final String id;
    private final int damage;
    private final String ownerId;

    public Bullet(String id, String ownerId, int damage) {
        this.id = id;
        this.damage = damage;
        this.ownerId = ownerId;
    }

    @Override
    public Optional<Bullet> collide(Collideable other) {
        return Optional.empty();
    }

    @Override
    public int getLives() {
        return 1;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getOwnerId(){
        return ownerId;
    }
}
