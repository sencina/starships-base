package factory;

import controller.ShipController;
import enums.BulletType;
import enums.WeaponType;
import generator.IdGenerator;
import generator.PositionGenerator;
import generator.SizeGenerator;
import generator.SpeedGenerator;
import model.*;
import movement.Mover;
import movement.Position;
import org.jetbrains.annotations.NotNull;
import static config.Constants.*;
import static config.PlayerShipsSettings.*;

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
        return new ShipController(createDefaultShipMover(new Position(400,400), 90), new ClassicWeapon(BULLETS_PER_SHOT,BulletType.valueOf(ACTUAL_BULLET)));
    }

    public static Weapon createWeapon(WeaponType type){
        return createWeapon(type, BulletType.valueOf(ACTUAL_BULLET));
    }

    public static Weapon createWeapon(WeaponType type, BulletType bulletType){
        return switch (type) {
            case CLASSIC_WEAPON -> new ClassicWeapon(BULLETS_PER_SHOT, bulletType);
            case DOUBLE_SIDED_WEAPON -> new DoubleSidedWeapon(bulletType);
        };
    }

    public static ShipController createShipController(int lives, ClassicWeapon weapon, Position position, double vector, int speed) {
        return new ShipController(new Mover<>(EntityFactory.createShip(lives),position, vector, speed), weapon);
    }

    public static ShipController createP1DefaultShipController(){
        return new ShipController(new Mover<>(new Ship("0", LIVES),new Position(P1_STARTING_X, P1_STARTING_Y), STARTING_ANGLE, STARTING_SPEED), createWeapon(WeaponType.valueOf(P1_WEAPON), BulletType.valueOf(P1_BULLET)));
    }

    public static ShipController createP2DefaultShipController(){
        return new ShipController(new Mover<>(new Ship("1", LIVES),new Position(P2_STARTING_X, P2_STARTING_Y), STARTING_ANGLE, STARTING_SPEED), createWeapon(WeaponType.valueOf(P2_WEAPON), BulletType.valueOf(P2_BULLET)));
    }

    public static Mover<Asteroid> createAsteroidMover(Position position, double rotation, double speed) {
        return new Mover<>(createAsteroid(), position, rotation, speed);
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

    private static <T extends Collideable> Mover<T> createMover(T entity, Position position, double vector, double speed) {
            return new Mover<T>(entity, position, vector, speed);
    }

    @NotNull
    public static Mover<Asteroid> spawnAsteroid(double gameWidth, double gameHeight) {
        Tuple<Position,Double> tuple = PositionGenerator.generateAsteroidPosition(gameWidth, gameHeight);
        return createAsteroidMover(tuple.first(), tuple.second(), SpeedGenerator.generateAsteroidSpeed());
    }

    private static double getBulletSpeed(BulletType bulletType) {
        return switch (bulletType) {
            case CUSTOM -> CUSTOM_BULLET_SPEED;
            default -> BULLET_SPEED;
        };
    }

}
