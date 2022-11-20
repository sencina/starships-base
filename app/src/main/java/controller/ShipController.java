package controller;

import kotlin.collections.ShortIterator;
import model.*;
import movement.Mover;
import movement.util.Coordinate;
import movement.util.Vector;

import java.util.Optional;

public class ShipController implements Collideable<ShipController> {

    private final Ship ship;
    private final Weapon weapon;

    public ShipController(Ship ship, Weapon weapon) {
        this.ship = ship;
        this.weapon = weapon;
    }

    public Mover<Bullet> shoot(Coordinate position, Vector vector) {
        return weapon.shoot(position, vector, ship.getId());
    }

    @Override
    public Optional<ShipController> collide(Collideable other) {

        Optional<Ship> shipOptional = ship.collide(other);
        if (shipOptional.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new ShipController(shipOptional.get(), weapon));
    }

    @Override
    public int getLives() {
        return ship.getLives();
    }

    @Override
    public int getDamage() {
        return 0;
    } //No hace daño

    @Override
    public String getId() {
        return ship.getId();
    }
}
