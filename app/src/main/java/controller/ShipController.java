package controller;

import edu.austral.ingsis.starships.ui.ElementModel;
import model.*;
import movement.Mover;
import movement.Position;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Optional;

public class ShipController implements Collideable<ShipController>, Showable {

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
        return Optional.of(new ShipController(new Mover<>(shipOptional.get(), shipMover.getPosition(),shipMover.getVector(), shipMover.getSpeed(), shipMover.getParser()), weapon));
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

    public ShipController move() {
        return new ShipController(shipMover.move(), weapon);
    }

    public ShipController rotate(double degrees) {
        return new ShipController(shipMover.rotate(degrees), weapon);
    }

    public Position getPosition() {
        return shipMover.getPosition();
    }

    public double getVector() {
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

    @Override
    public ElementModel toElementModel() {
        return shipMover.toElementModel();
    }

    public ShipController updatePosition(Position position) {
        return new ShipController(new Mover<>(shipMover.getEntity(), position, shipMover.getVector(), shipMover.getSpeed(), shipMover.getParser()), weapon);
    }

    public ShipController accelerate() {
        return new ShipController(shipMover.accelerate(), weapon);
    }

    public ShipController updateMover(Collideable entity1) {
        return new ShipController(new Mover<>((Ship) entity1,getPosition(),getRotationInDegrees(),getSpeed(),getShipMover().getParser()), weapon);
    }
}
