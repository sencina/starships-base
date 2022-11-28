package parser;

import edu.austral.ingsis.starships.ui.ElementColliderType;
import edu.austral.ingsis.starships.ui.ElementModel;
import edu.austral.ingsis.starships.ui.ImageRef;
import enums.BulletType;
import model.Bullet;
import movement.Mover;
import static config.Constants.*;

public class BulletParser implements EntityParser<Bullet> {

    @Override
    public ElementModel toElementModel(Mover<Bullet> entity) {
        return switch (entity.getEntity().getBulletType()){
            case BULLET -> new ElementModel(entity.getId(), entity.getPosition().getX(), entity.getPosition().getY(), BULLET_SIZE, BULLET_SIZE, entity.getRotationInDegrees(), BULLET_COLLIDER_TYPE,BULLET_IMAGE_REF);
            case LASER -> new ElementModel(entity.getId(), entity.getPosition().getX(), entity.getPosition().getY(), LASER_SIZE, LASER_SIZE, entity.getRotationInDegrees(),BULLET_COLLIDER_TYPE,LASER_IMAGE_REF);
            case ROCKET -> new ElementModel(entity.getId(), entity.getPosition().getX(), entity.getPosition().getY(), ROCKET_SIZE, ROCKET_SIZE, entity.getRotationInDegrees(), BULLET_COLLIDER_TYPE,ROCKET_IMAGE_REF);
            case PRISON_MIKE -> new ElementModel(entity.getId(), entity.getPosition().getX(), entity.getPosition().getY(), PRISON_MIKE_SIZE, PRISON_MIKE_SIZE, entity.getRotationInDegrees(), BULLET_COLLIDER_TYPE,PRISON_MIKE_IMAGE_REF);
            case CUSTOM -> new ElementModel(entity.getId(), entity.getPosition().getX(), entity.getPosition().getY(),CUSTOM_BULLET_SIZE, CUSTOM_BULLET_SIZE,entity.getRotationInDegrees(), BULLET_COLLIDER_TYPE,CUSTOM_BULLET_IMAGE_REF);
        };
    }
}
