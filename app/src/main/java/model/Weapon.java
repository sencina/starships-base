package model;

import enums.BulletType;
import factory.EntityFactory;
import movement.Mover;
import movement.Position;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Weapon implements Serializable{

    private final BulletType bulletType;
    private final int bulletsPerShot;

    public Weapon(int bulletsPerShot, BulletType bulletType) {
        this.bulletsPerShot = bulletsPerShot;
        this.bulletType = bulletType;
    }

    public BulletType getBulletType() {
        return bulletType;
    }

    public List<Mover<Bullet>> shoot(Position position, double vector, String ownerId) {
       List<Mover<Bullet>> bullets = new ArrayList<>();
         for (int i = 0; i < bulletsPerShot; i++) {
              bullets.add(EntityFactory.createBulletMover(bulletType, new Position(position.getX() + i*20, position.getY() + i*10), vector, ownerId));
         }
         return bullets;
    }


    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bulletsPerShot", bulletsPerShot);
        jsonObject.put("bulletType", bulletType.toString());
        return jsonObject;
    }
}
