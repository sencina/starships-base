package model;

import java.util.Optional;

public class Ship implements Collideable<Ship>, Entity {

    private final String id;
    private final int lives;

    public Ship(String id, int lives) {
        this.id = id;
        this.lives = lives;
    }

    @Override
    public Optional<Ship> collide(Collideable other) {
        int damage = other.getDamage() == 0 ? 1 : other.getDamage();
        int life = other.getDamage()/damage;
        if (lives - life <= 0) {
            return Optional.empty();
        }
        return Optional.of(new Ship(id, lives - life));//0 si choca con una nave, 1 si choca con algo que no sea una nave
    }

    @Override
    public int getLives() {
        return lives;
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
