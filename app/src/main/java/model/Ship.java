package model;

import edu.austral.ingsis.starships.ui.ElementModel;
import org.json.simple.JSONObject;

import java.util.Optional;

public class Ship implements Collideable<Ship>, Entity {

    private final String id;
    private final int lives;

    public Ship(String id, int lives) {
        this.id = "starship-"+id;
        this.lives = lives;
    }

    @Override
    public Optional<Ship> collide(Collideable other) {
        int damage = other.getDamage() == 0 ? 1 : other.getDamage();
        int life = other.getDamage()/damage;
        if (lives - life <= 0) {
            return Optional.empty();
        }
        return Optional.of(new Ship(getIdNumber(), lives - life));//0 si choca con una nave, 1 si choca con algo que no sea una nave
    }

    @Override
    public int getLives() {
        return lives;
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("lives", lives);
        return jsonObject;
    }
}
