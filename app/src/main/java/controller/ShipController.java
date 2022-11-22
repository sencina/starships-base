package controller;

import model.*;
import movement.Mover;
import movement.util.Position;
import movement.util.Vector;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Optional;

public class ShipController implements Collideable<ShipController> {

    private final Mover<Ship> shipMover;
    private final Weapon weapon;

    public ShipController(Mover<Ship> shipMover, Weapon weapon) {
        this.shipMover = shipMover;
        this.weapon = weapon;
    }

    public List<Mover<Bullet>> shoot() {
        return weapon.shoot(shipMover.getPosition(), shipMover.getVector(), shipMover.getId());
    }

    @Override
    public Optional<ShipController> collide(Collideable other) {

        Optional<Ship> shipOptional = shipMover.getEntity().collide(other);
        if (shipOptional.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new ShipController(new Mover<>(shipOptional.get(), shipMover.getPosition(),shipMover.getVector(), shipMover.getSpeed()), weapon));
    }

    @Override
    public int getLives() {
        return shipMover.getEntity().getLives();
    }

    @Override
    public int getDamage() {
        return 0;
    } //No hace daño

    @Override
    public String getId() {
        return shipMover.getId();
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("shipMover", shipMover.toJson());
        jsonObject.put("weapon", weapon.toJson());
        return jsonObject;
    }

    public ShipController move(double time) {
        return new ShipController(shipMover.move(time), weapon);
    }

    public ShipController rotate(double degrees) {
        return new ShipController(shipMover.rotate(degrees), weapon);
    }

    public Position getPosition() {
        return shipMover.getPosition();
    }

    public Vector getVector() {
        return shipMover.getVector();
    }

    public double getRotationInDegrees() {
        return shipMover.getRotationInDegrees();
    }

    public double getSpeed() {
        return shipMover.getSpeed();
    }

    public ShipController stop() {
        return new ShipController(shipMover.stop(), weapon);
    }

    public Mover<Ship> getShipMover() {
        return shipMover;
    }
}
