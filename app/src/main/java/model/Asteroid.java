package model;

import org.json.simple.JSONObject;

import java.util.Optional;

public class Asteroid implements Collideable<Asteroid>{

    private final String id;
    private final int size;

    public Asteroid(String id, int size) {
        this.id = "asteroid-"+id;
        this.size = size;
    }

    @Override
    public Optional<Asteroid> collide(Collideable other) {

        if (other.getIdType().equals("asteroid")) {
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
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("size", size);
        return jsonObject;
    }
}
