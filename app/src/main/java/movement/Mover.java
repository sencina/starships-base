package movement;

import edu.austral.ingsis.starships.ui.ElementModel;
import model.Collideable;
import model.Movable;
import model.Showable;
import movement.util.Position;
import movement.util.Vector;
import org.json.simple.JSONObject;
import parser.EntityParser;

public class Mover<T extends Collideable<T>> implements Movable, Showable {
    private final T entity;
    private final Position position;
    private final Vector direction;
    private final double speed;

    private final EntityParser<T> parser;
    public Mover(T entity, Position position, Vector direction, double speed, EntityParser<T> parser) {
        this.entity = entity;
        this.position = position;
        this.direction = direction;
        this.speed = speed;
        this.parser = parser;
    }



    public T getEntity(){
        return entity;
    }
    @Override
    public String getId() {
        return entity.getId();
    }

    @Override
    public Mover<T> move() {
        return new Mover<>(entity, position.move(direction,speed), direction, speed <= 50 ? speed + 10 : speed, parser);
    }

    @Override
    public Mover<T> rotate(double degrees) {
        return new Mover<>(entity, position, direction.rotate(degrees), speed, parser);
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
        return new Mover<>(entity, position, direction, 0, parser);
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

    @Override
    public ElementModel toElementModel() {
        return parser.toElementModel(this);
    }

    public EntityParser<T> getParser() {
        return parser;
    }
}
