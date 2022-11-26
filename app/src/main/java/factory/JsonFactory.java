package factory;

import controller.ShipController;
import enums.BulletType;
import enums.EntityType;
import model.Asteroid;
import model.Bullet;
import model.Ship;
import model.Weapon;
import movement.Mover;
import movement.Position;
import org.json.simple.JSONObject;
import parser.AsteroidParser;
import parser.BulletParser;
import parser.ShipControllerParser;

import static config.Constants.BULLET_SPEED;
import static config.Constants.CUSTOM_BULLET_SPEED;

public class JsonFactory {

    public static Mover createMoverFromJson(JSONObject mover) {

        EntityType type = EntityType.valueOf((String) mover.get("entityType"));

        Position position = new Position((double) mover.get("x"), (double) mover.get("y"));
        double rotationInDegrees = (double) mover.get("angle");
        double speed = (double) mover.get("speed");

        return switch (type) {
            case BULLET -> new Mover<Bullet>(createBulletFromJson((JSONObject) mover.get("entity")), position, rotationInDegrees, speed, new BulletParser());
            case ASTEROID -> new Mover<Asteroid>(createAsteroidFromJson((JSONObject) mover.get("entity")), position, rotationInDegrees, speed, new AsteroidParser());
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }
    private static Asteroid createAsteroidFromJson(JSONObject entity) {
        return new Asteroid(getIdNumber(entity), (int) (long) entity.get("size"));
    }

    private static String getIdNumber(JSONObject entity) {
        return ((String) entity.get("id")).split("-")[1];
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
        double rotationInDegreees = (double) shipMover.get("angle");
        double speed = (double) shipMover.get("speed");

        return new ShipController(new Mover<>(createShipFromJson(ship), position, rotationInDegreees, speed, new ShipControllerParser()), createWeaponFromJson(weapon));
    }
}
