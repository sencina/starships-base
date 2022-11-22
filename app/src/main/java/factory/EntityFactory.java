package factory;

import controller.ShipController;
import enums.BulletType;
import generator.IdGenerator;
import model.*;
import movement.Mover;
import movement.util.Position;
import movement.util.Vector;
import org.json.simple.JSONObject;

public class EntityFactory {

        private static Bullet createBullet(BulletType bulletType, String ownerId) {
            return switch (bulletType) {
                case BULLET -> new Bullet(IdGenerator.generateId(), ownerId,10);
                case LASER -> new Bullet(IdGenerator.generateId(), ownerId, 25);
                case ROCKET -> new Bullet(IdGenerator.generateId(), ownerId, 50);
            };
        }

        public static Mover<Bullet> createBulletMover(BulletType bulletType, Position position, Vector vector, String ownerId) {
            return new Mover<>(createBullet(bulletType, ownerId), position, vector, 20);
        }

    public static Mover createMoverFromJson(JSONObject mover) {

            String type = ((String) mover.get("id")).split("-")[0];

            Position position = new Position((double) mover.get("x"), (double) mover.get("y"));
            Vector vector = new Vector((double) mover.get("angle"));
            double speed = (double) mover.get("speed");

            return switch (type) {
                case "bullet" -> new Mover<Bullet>(createBulletFromJson((JSONObject) mover.get("entity")), position, vector, speed);
                case "asteroid" -> new Mover<Asteroid>(createAsteroidFromJson((JSONObject) mover.get("entity")), position, vector, speed);
                default -> throw new IllegalStateException("Unexpected value: " + type);
            };
    }

    public static ShipController createDefaultShipControllerForTesting(){
        return new ShipController(createDefaultShipMover(new Position(0,0), new Vector(1,1)), new Weapon(1,BulletType.BULLET));

    }

    private static Mover<Ship> createDefaultShipMover(Position position, Vector vector) {
            return new Mover<>(new Ship(IdGenerator.generateId(),3), position, vector, 10);
    }

    private static Asteroid createAsteroidFromJson(JSONObject entity) {
            return new Asteroid(((String) entity.get("id")).split("-")[1], (int) entity.get("size"));
    }

    private static Bullet createBulletFromJson(JSONObject entity) {
        return new Bullet(((String) entity.get("id")).split("-")[1], (String) entity.get("ownerId"), (int) (long) entity.get("damage"));
    }

    private static Weapon createWeaponFromJson(JSONObject weapon) {

        int bulletsPerShot = (int) (long) weapon.get("bulletsPerShot");
        BulletType bulletType = BulletType.valueOf((String) weapon.get("bulletType"));
        return new Weapon(bulletsPerShot, bulletType);
    }

    private static Ship createShipFromJson(JSONObject entity) {

        String id = ((String) entity.get("id")).split("-")[1];
        int lives = (int) (long) entity.get("lives");
        return new Ship(id, lives);
    }

    public static ShipController createShipControllerFromJson(JSONObject jo) {

            JSONObject shipMover = (JSONObject) jo.get("shipMover");
            JSONObject ship = (JSONObject) shipMover.get("entity");
            JSONObject weapon = (JSONObject) jo.get("weapon");

            Position position = new Position((double) shipMover.get("x"), (double) shipMover.get("y"));
            Vector vector = new Vector((double) shipMover.get("angle"));
            double speed = (double) shipMover.get("speed");

            return new ShipController(new Mover<>(createShipFromJson(ship), position, vector, speed), createWeaponFromJson(weapon));
    }
}
