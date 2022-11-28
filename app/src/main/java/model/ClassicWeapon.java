package model;

import enums.BulletType;
import enums.WeaponType;
import factory.EntityFactory;
import movement.Mover;
import movement.Position;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static config.Constants.*;

public class ClassicWeapon implements Weapon{

    private final BulletType bulletType;
    private final int bulletsPerShot;

    private final WeaponType weaponType;

    public ClassicWeapon(int bulletsPerShot, BulletType bulletType) {
        this.bulletsPerShot = bulletsPerShot;
        this.bulletType = bulletType;
        this.weaponType = WeaponType.CLASSIC_WEAPON;
    }

    public BulletType getBulletType() {
        return bulletType;
    }

    public List<Mover<Bullet>> shoot(Position position, double angle, String ownerId) {
       List<Mover<Bullet>> bullets = new ArrayList<>();
         for (int i = 0; i < bulletsPerShot; i++) {
              bullets.add(EntityFactory.createBulletMover(bulletType, createBulletPosition(position, angle, i), angle, ownerId));
         }
         return bullets;
    }

    private Position createBulletPosition(Position position, double angle, int offset) {
        double x = position.getX() - Math.sin(angle) * offset*30; //- Math.cos(Math.toRadians(angle)) * SHIP_WIDTH;
        double y = position.getY() + Math.cos(angle) * offset*30; //+ Math.sin(Math.toRadians(angle)) * SHIP_HEIGHT;
        return new Position(x, y);
    }


    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bulletsPerShot", bulletsPerShot);
        jsonObject.put("bulletType", bulletType.toString());
        jsonObject.put("weaponType", weaponType.toString());
        return jsonObject;
    }
}
