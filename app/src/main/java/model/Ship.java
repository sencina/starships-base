package model;

public class Ship implements Collideable, Entity {

    private final String id;
    private final int health;

    public Ship(String id, int health) {
        this.id = id;
        this.health = health;
    }

    @Override
    public Ship collide(Collideable other) {
        return new Ship(id, health - other.getDamage());
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public String getId() {
        return id;
    }
}
