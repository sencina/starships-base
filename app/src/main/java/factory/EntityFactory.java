package factory;

import controller.ShipController;
import enums.BulletType;
import enums.EntityType;
import generator.IdGenerator;
import generator.SizeGenerator;
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
        return new Mover<>(createBullet(bulletType, ownerId), position, vector, getBulletSpeed(bulletType));
    }


    public static ShipController createDefaultShipControllerForTesting(){
        return new ShipController(createDefaultShipMover(new Position(400,400), 90), new Weapon(BULLETS_PER_SHOT,BulletType.valueOf(ACTUAL_BULLET)));
    }

    public static ShipController createShipController(int lives, Weapon weapon, Position position, double vector, int speed) {
        return new ShipController(new Mover<>(EntityFactory.createShip(lives),position, vector, speed), weapon);
    }

    public static ShipController createP1DefaultShipController(){
        return createShipController(LIVES,new Weapon(BULLETS_PER_SHOT, BulletType.valueOf(ACTUAL_BULLET)), new Position(P1_STARTING_X,P1_STARTING_Y), STARTING_ANGLE, STARTING_SPEED);
    }

    public static ShipController createP2DefaultShipController(){
        return createShipController(LIVES,new Weapon(BULLETS_PER_SHOT, BulletType.valueOf(ACTUAL_BULLET)), new Position(P2_STARTING_X,P2_STARTING_Y), STARTING_ANGLE, STARTING_SPEED);
    }

    public static Mover<Asteroid> createAsteroidMover(Position position, double rotation) {
        return new Mover<>(createAsteroid(), position, rotation, ASTEROID_SPEED);
    }

    private static Asteroid createAsteroid() {
        return new Asteroid(IdGenerator.generateId(), SizeGenerator.generateAsteroidSize());
    }

    public static Ship createShip(int lives) {
        return new Ship(IdGenerator.generateStarshipId(), lives);
    }

    public static Mover<Ship> createDefaultShipMover(Position position, double vector) {
        return new Mover<>(createShip(LIVES), position, vector, STARTING_SPEED);
    }

    private static <T extends Collideable<T>> Mover<T> createMover(T entity, Position position, double vector, double speed) {
            return new Mover<T>(entity, position, vector, speed);
    }

    @NotNull
    public static Mover<Asteroid> spawnAsteroid(double gameWidth, double gameHeight) {
        int x = (int) (Math.random() * gameWidth);
        int y = (int) (Math.random() * gameHeight);
        double vector = Math.random() * 360;
        return createAsteroidMover(new Position(x,y), vector);
    }

    private static double getBulletSpeed(BulletType bulletType) {
        return switch (bulletType) {
            case CUSTOM -> CUSTOM_BULLET_SPEED;
            default -> BULLET_SPEED;
        };
    }

    private static EntityParser parserFromEntityType(EntityType type) {
        return switch (type) {
            case BULLET -> new BulletParser();
            case ASTEROID -> new AsteroidParser();
            case STARSHIP -> new ShipControllerParser();
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

}
