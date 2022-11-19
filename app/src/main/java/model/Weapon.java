package model;

import enums.BulletType;
import manager.IdGenerator;
import movement.Mover;
import movement.util.Coordinate;
import movement.util.Vector;

import java.util.ArrayList;
import java.util.List;

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

    public Mover<Bullet> shoot(Coordinate position, Vector vector) {
        return new Mover<Bullet>(new Bullet(IdGenerator.generateId(), damage), position, vector, 20);
    }

    public int getDamage() {
        return damage;
    }
}
