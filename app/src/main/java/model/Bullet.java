package model;

import enums.BulletType;
import enums.EntityType;
import org.json.simple.JSONObject;

import java.util.Optional;

public class Bullet implements Collideable{

    private final String id;
    private final int damage;
    private final String ownerId;

    private final BulletType bulletType;

    private final EntityType entityType;

    public Bullet(String id, String ownerId, int damage) {
        this(id,ownerId,damage, BulletType.BULLET);
    }

    public Bullet(String id, String ownerId, int damage, BulletType bulletType) {
        this.entityType = EntityType.BULLET;
        this.id = entityType+"-"+id;
        this.damage = damage;
        this.ownerId = ownerId;
        this.bulletType = bulletType;
    }

    @Override
    public Optional<Collideable> collide(Collideable other) {
        if (other.getEntityType().equals(getEntityType()) || other.getId().equals(ownerId)) {
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
    public Tuple<String,Integer> getPoints() {
        return new Tuple<>(ownerId, damage);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public EntityType getEntityType() {
        return entityType;
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
        jsonObject.put("bulletType", bulletType);
        jsonObject.put("entityType", entityType.toString());
        return jsonObject;
    }

    public BulletType getBulletType(){
        return bulletType;
    }

}
