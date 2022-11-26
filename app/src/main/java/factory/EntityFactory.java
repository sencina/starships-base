package factory;

import controller.ShipController;
import enums.BulletType;
import enums.EntityType;
import generator.IdGenerator;
import model.*;
import movement.Mover;
import movement.Position;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import parser.AsteroidParser;
import parser.BulletParser;
import parser.EntityParser;
import parser.ShipControllerParser;
import static config.Constants.*;

public class EntityFactory {

    private static Bullet createBullet(BulletType bulletType, String ownerId) {
            return switch (bulletType) {
                case BULLET -> new Bullet(IdGenerator.generateId(), ownerId,BULLET_DAMAGE, bulletType);
                case LASER -> new Bullet(IdGenerator.generateId(), ownerId, LASER_DAMAGE, bulletType);
                case ROCKET -> new Bullet(IdGenerator.generateId(), ownerId, ROCKET_DAMAGE, bulletType);
                case PRISON_MIKE -> new Bullet(IdGenerator.generateId(), ownerId, PRISON_MIKE_DAMAGE, bulletType);
                case CUSTOM -> new Bullet(IdGenerator.generateId(), ownerId, CUSTOM_BULLET_DAMAGE, bulletType);
            };
        }

    public static Mover<Bullet> createBulletMover(BulletType bulletType, Position position, double vector, String ownerId) {
        return new Mover<>(createBullet(bulletType, ownerId), position, vector, getBulletSpeed(bulletType), new BulletParser());
    }
    private static double getBulletSpeed(BulletType bulletType) {
        return switch (bulletType) {
            case CUSTOM -> CUSTOM_BULLET_SPEED;
            default -> BULLET_SPEED;
        };
    }

    public static Mover createMoverFromJson(JSONObject mover) {

            String type = ((String) mover.get("id")).split("-")[0];

            Position position = new Position((double) mover.get("x"), (double) mover.get("y"));
            double rotationInDegrees = (double) mover.get("angle");
            double speed = (double) mover.get("speed");

            return switch (type) {
                case "bullet" -> new Mover<Bullet>(createBulletFromJson((JSONObject) mover.get("entity")), position, rotationInDegrees, speed, new BulletParser());
                case "asteroid" -> new Mover<Asteroid>(createAsteroidFromJson((JSONObject) mover.get("entity")), position, rotationInDegrees, speed, new AsteroidParser());
                default -> throw new IllegalStateException("Unexpected value: " + type);
            };
    }

    public static ShipController createDefaultShipControllerForTesting(){
        return new ShipController(createDefaultShipMover(new Position(400,400), 90), new Weapon(BULLETS_PER_SHOT,BulletType.valueOf(ACTUAL_BULLET)));
    }

    public static ShipController createShipController(int lives, Weapon weapon, Position position, double vector, int speed) {
        return new ShipController(new Mover<>(EntityFactory.createShip(lives),position, vector, speed, new ShipControllerParser()), weapon);
    }

    public static ShipController createP1DefaultShipController(){
        return createShipController(LIVES,new Weapon(BULLETS_PER_SHOT, BulletType.valueOf(ACTUAL_BULLET)), new Position(P1_STARTING_X,P1_STARTING_Y), STARTING_ANGLE, STARTING_SPEED);
    }

    public static ShipController createP2DefaultShipController(){
        return createShipController(LIVES,new Weapon(BULLETS_PER_SHOT, BulletType.valueOf(ACTUAL_BULLET)), new Position(P2_STARTING_X,P2_STARTING_Y), STARTING_ANGLE, STARTING_SPEED);
    }

    public static Mover<Asteroid> createAsteroidMover(Position position, double rotation) {
        return new Mover<>(createAsteroid(), position, rotation, ASTEROID_SPEED, new AsteroidParser());
    }

    private static Asteroid createAsteroid() {
        return new Asteroid(IdGenerator.generateId(), ASTEROID_SIZE);
    }

    public static Ship createShip(int lives) {
        return new Ship(IdGenerator.generateStarshipId(), lives);
    }

    public static Mover<Ship> createDefaultShipMover(Position position, double vector) {
        return new Mover<>(createShip(LIVES), position, vector, STARTING_SPEED, new ShipControllerParser());
    }

    private static <T extends Collideable<T>> Mover<T> createMover(T entity, Position position, double vector, double speed) {
            return new Mover<T>(entity, position, vector, speed, parserFromEntityType(entity.getEntityType()));
    }

    private static EntityParser parserFromEntityType(EntityType type) {
        return switch (type) {
            case BULLET -> new BulletParser();
            case ASTEROID -> new AsteroidParser();
            case STARSHIP -> new ShipControllerParser();
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    private static Asteroid createAsteroidFromJson(JSONObject entity) {
            return new Asteroid(((String) entity.get("id")).split("-")[1], (int) (long) entity.get("size"));
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

    @NotNull
    public static Mover<Asteroid> spawnAsteroid(int gameWidth, int gameHeight) {
        int x = (int) (Math.random() * gameWidth);
        int y = (int) (Math.random() * gameHeight);
        double vector = Math.random() * 360;
        return createAsteroidMover(new Position(x,y), vector);
    }
}
