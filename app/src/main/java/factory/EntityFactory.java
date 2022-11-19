package factory;

import controller.ShipController;
import enums.BulletType;
import model.Ship;
import model.Weapon;
import movement.Mover;
import movement.util.Coordinate;
import movement.util.Vector;

public class EntityFactory {

    public static ShipController createShip(String id, int health) {
        return new ShipController(new Mover<>(new Ship(id, health), new Coordinate(400,400), new Vector(0,1), 10), new Weapon(10, BulletType.BULLET));
    }
}
