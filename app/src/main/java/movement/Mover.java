package movement;

import model.Collideable;
import model.Entity;
import model.Movable;
import movement.util.Coordinate;
import movement.util.Vector;

public class Mover<T extends Collideable<T>> implements Movable {

    private final T entity;
    private final Coordinate position;
    private final Vector direction;
    private final double speed;

    public Mover(T entity, Coordinate position, Vector direction, double speed) {
        this.entity = entity;
        this.position = position;
        this.direction = direction;
        this.speed = speed;
    }

    public T getEntity(){
        return entity;
    }
    @Override
    public String getId() {
        return entity.getId();
    }

    @Override
    public Mover<T> move(double time) {
        return new Mover<>(entity, position.move(direction, time,speed), direction, speed);
    }

    @Override
    public Mover<T> rotate(double degrees) {
        return new Mover<>(entity, position, direction.rotate(degrees), speed);
    }

    @Override
    public Coordinate getPosition() {
        return position;
    }

    @Override
    public Vector getVector() {
        return direction;
    }

    @Override
    public double getRotationInDegrees() {
        return direction.getAngleInDegrees();
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public Mover<T> stop() {
        return new Mover<>(entity, position, direction, 0);
    }

}
