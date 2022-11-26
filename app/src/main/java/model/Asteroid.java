package model;

import enums.EntityType;
import org.json.simple.JSONObject;

import java.util.Optional;

public class Asteroid implements Collideable<Asteroid>, Entity{

    private final String id;
    private final int size;

    private final EntityType type;

    public Asteroid(String id, int size) {
        this.type = EntityType.ASTEROID;
        this.id = type+"-"+id;
        this.size = size;
    }

    @Override
    public Optional<Asteroid> collide(Collideable other) {

        if (other.getEntityType().equals(type)) {
            return Optional.of(this);
        }
        if (size - other.getDamage() <= 0) {
            return Optional.empty();
        }
        return Optional.of(new Asteroid(getIdNumber(), size - other.getDamage()));
    }

    @Override
    public int getLives() {
        return size;
    }

    @Override
    public int getDamage() {
        return size;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public EntityType getEntityType() {
        return type;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("size", size);
        return jsonObject;
    }
}
