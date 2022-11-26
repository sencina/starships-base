package movement;

import edu.austral.ingsis.starships.ui.ElementModel;
import enums.EntityType;
import model.Collideable;
import model.Movable;
import model.Showable;
import org.json.simple.JSONObject;
import parser.EntityParser;

import static config.Constants.MAX_SPEED;
import static config.Constants.SPEED_INCREMENT;

public class Mover<T extends Collideable<T>> implements Movable, Showable {
    private final T entity;
    private final Position position;
    private final double rotationInDegrees;
    private final double speed;

    private final EntityParser<T> parser;
    public Mover(T entity, Position position, double rotationInDegrees, double speed, EntityParser<T> parser) {
        this.entity = entity;
        this.position = position;
        this.rotationInDegrees = rotationInDegrees;
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
    public EntityType getEntityType() {
        return entity.getEntityType();
    }

    @Override
    public Mover<T> move() {
        return new Mover<>(entity, position.move(rotationInDegrees,speed), rotationInDegrees, speed, parser);
    }

    @Override
    public Mover<T> accelerate() {
        return new Mover<>(entity, position.move(rotationInDegrees,speed), rotationInDegrees, speed <= MAX_SPEED ? speed + SPEED_INCREMENT : speed, parser);
    }

    @Override
    public Mover<T> rotate(double degrees) {
        return new Mover<>(entity, position, rotationInDegrees+degrees, speed, parser);
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public double getRotationInDegrees() {
        return rotationInDegrees;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public Mover<T> stop() {
        return new Mover<>(entity, position, rotationInDegrees, 0, parser);
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("entity", entity.toJson());
        jsonObject.put("x", position.getX());
        jsonObject.put("y", position.getY());
        jsonObject.put("angle", getRotationInDegrees());
        jsonObject.put("speed", speed);
        jsonObject.put("entityType", getEntityType().toString());
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

    public int getLives() {
        return entity.getLives();
    }
}
