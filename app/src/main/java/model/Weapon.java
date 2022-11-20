package model;

import enums.BulletType;
import generator.IdGenerator;
import movement.Mover;
import movement.util.Coordinate;
import movement.util.Vector;

public class Weapon {

    private final BulletType bulletType;
    private final int damage;

    public Weapon(int damage, BulletType bulletType) {
        this.damage = damage;
        this.bulletType = bulletType;
    }

    public BulletType getBulletType() {
        return bulletType;
    }

    public Mover<Bullet> shoot(Coordinate position, Vector vector, String ownerId) {
       return new Mover<>(new Bullet(IdGenerator.generateId(), ownerId, damage), position, vector, 20);
    }

    public int getDamage() {
        return damage;
    }
}
