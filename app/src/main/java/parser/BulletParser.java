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
        return new ElementModel(entity.getId(), entity.getPosition().getX(), entity.getPosition().getY(), BULLET_SIZE, BULLET_SIZE, entity.getRotationInDegrees(), BULLET_COLLIDER_TYPE, addImageRef(entity.getEntity().getBulletType()));
    }

    private ImageRef addImageRef(BulletType type) {
        return new ImageRef(type.toString().toLowerCase(), BULLET_SIZE , BULLET_SIZE);
    }

}
