package model;

import movement.util.Coordinate;
import movement.util.Vector;

public class Bullet implements Collideable<Bullet>, Entity{

    private final String id;
    private final int damage;

    public Bullet(String id, int damage) {
        this.id = id;
        this.damage = damage;
    }

    @Override
    public Bullet collide(Collideable other) {
        return this;
    }

    @Override
    public int getHealth() {
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
}
