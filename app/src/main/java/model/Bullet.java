package model;

import enums.BulletType;
import org.json.simple.JSONObject;

import java.util.Optional;

public class Bullet implements Collideable<Bullet>{

    private final String id;
    private final int damage;
    private final String ownerId;

    private final BulletType type;

    public Bullet(String id, String ownerId, int damage) {
        this.id = "bullet-"+id;
        this.damage = damage;
        this.ownerId = ownerId;
        this.type = BulletType.BULLET;
    }

    public Bullet(String id, String ownerId, int damage, BulletType type) {
        this.id = "bullet-"+id;
        this.damage = damage;
        this.ownerId = ownerId;
        this.type = type;
    }

    @Override
    public Optional<Bullet> collide(Collideable other) {
        if (other.getIdType().equals("bullet")) {
            return Optional.of(this);
        }
        return Optional.empty();
    }

    @Override
    public int getLives() {
        return 1;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getOwnerId(){
        return ownerId;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("damage", damage);
        jsonObject.put("ownerId", ownerId);
        return jsonObject;
    }

    public BulletType getType(){
        return type;
    }
}
