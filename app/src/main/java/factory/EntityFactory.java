package factory;

import controller.ShipController;
import enums.BulletType;
import model.Ship;
import model.Weapon;
import movement.Mover;
import movement.util.Coordinate;
import movement.util.Vector;

public class EntityFactory {

        public static ShipController createShip(String id, int lives) {
            return new ShipController(new Ship(id, lives), new Weapon(10, BulletType.BULLET));
        }
}
