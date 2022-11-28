package model;

import enums.BulletType;
import enums.WeaponType;
import factory.EntityFactory;
import movement.Mover;
import movement.Position;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static config.Constants.SHIP_HEIGHT;
import static config.Constants.SHIP_WIDTH;

public class DoubleSidedWeapon implements Weapon{

    private final BulletType bulletType;
    private final WeaponType weaponType;

    public DoubleSidedWeapon(BulletType bulletType) {
        this.bulletType = bulletType;
        this.weaponType = WeaponType.DOUBLE_SIDED_WEAPON;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bulletType", bulletType.toString());
        jsonObject.put("weaponType", weaponType.toString());
        return jsonObject;
    }

    @Override
    public List<Mover<Bullet>> shoot(Position position, double angle, String ownerId) {
        List<Mover<Bullet>> toReturn = new ArrayList<>();
        toReturn.add(EntityFactory.createBulletMover(bulletType, createBulletPosition(position,angle,0), angle, ownerId));
        toReturn.add(EntityFactory.createBulletMover(bulletType, createBulletPosition(position,angle+180,0), angle + 180, ownerId));
        return toReturn;
    }

    private Position createBulletPosition(Position position, double angle, int offset) {
        double x = position.getX() - Math.sin(angle) * offset*30; //- Math.cos(Math.toRadians(angle)) * SHIP_WIDTH;
        double y = position.getY() + Math.cos(angle) * offset*30; //+ Math.sin(Math.toRadians(angle)) * SHIP_HEIGHT;
        return new Position(x, y);
    }


}
