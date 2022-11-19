package controller;

import manager.IdGenerator;
import model.*;
import movement.Mover;
import movement.util.Coordinate;
import movement.util.Vector;

public class ShipController implements Collideable<ShipController>, Movable<ShipController> {

    private final Mover<Ship> shipMover;
    private final Weapon weapon;

    public ShipController(Mover<Ship> shipMover, Weapon weapon) {
        this.shipMover = shipMover;
        this.weapon = weapon;
    }

    public Mover<Bullet> shoot() {
        return weapon.shoot(shipMover.getPosition(), shipMover.getVector());
    }

    @Override
    public ShipController collide(Collideable other) {
        return new ShipController(new Mover<>(shipMover.getEntity().collide(other), shipMover.getPosition(), shipMover.getVector(), shipMover.getSpeed()), weapon);
    }

    @Override
    public int getHealth() {
        return shipMover.getEntity().getHealth();
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public ShipController move(double time) {
        return new ShipController(shipMover.move(time), weapon);
    }

    @Override
    public ShipController rotate(double degrees) {
        return new ShipController(shipMover.rotate(degrees), weapon);
    }

    @Override
    public String getId() {
        return shipMover.getId();
    }

    @Override
    public Coordinate getPosition() {
        return shipMover.getPosition();
    }

    @Override
    public Vector getVector() {
        return shipMover.getVector();
    }


    @Override
    public double getRotationInDegrees() {
        return shipMover.getRotationInDegrees();
    }

    @Override
    public double getSpeed() {
        return shipMover.getSpeed();
    }
}
