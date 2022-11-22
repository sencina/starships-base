package movement;

import model.Collideable;
import model.Movable;
import movement.util.Position;
import movement.util.Vector;
import org.json.simple.JSONObject;

public class Mover<T extends Collideable<T>> implements Movable {

    private final T entity;
    private final Position position;
    private final Vector direction;
    private final double speed;

    public Mover(T entity, Position position, Vector direction, double speed) {
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
    public Position getPosition() {
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

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("entity", entity.toJson());
        jsonObject.put("x", position.getX());
        jsonObject.put("y", direction.getY());
        jsonObject.put("angle", getRotationInDegrees());
        jsonObject.put("speed", speed);
        jsonObject.put("id", getId());
        return jsonObject;
    }
}
