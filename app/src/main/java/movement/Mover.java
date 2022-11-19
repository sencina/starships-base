package movement;

import model.Entity;
import model.Movable;
import movement.util.Coordinate;
import movement.util.Vector;

public class Mover<T extends Entity> implements Movable<Mover<T>> {

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
    public Mover move(double time) {
        return new Mover<>(entity,position.move(direction, time, speed), direction, speed);
    }

    @Override
    public Mover rotate(double degrees){
        return new Mover<>(entity, position, direction.rotate(degrees), speed);
    }

    @Override
    public String getId() {
        return entity.getId();
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

    public Mover<T> stop() {
        return new Mover<>(entity, position, direction, 0);
    }
}
