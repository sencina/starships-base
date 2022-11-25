package parser;

import edu.austral.ingsis.starships.ui.ElementColliderType;
import edu.austral.ingsis.starships.ui.ElementModel;
import edu.austral.ingsis.starships.ui.ImageRef;
import enums.BulletType;
import model.Bullet;
import movement.Mover;

public class BulletParser implements EntityParser<Bullet> {

    @Override
    public ElementModel toElementModel(Mover<Bullet> entity) {
        return new ElementModel(entity.getId(), entity.getPosition().getX(), entity.getPosition().getY(), 10, 10, entity.getRotationInDegrees(), ElementColliderType.Rectangular, addImageRef(entity.getEntity().getType()));
    }

    private ImageRef addImageRef(BulletType type) {
        return new ImageRef(type.toString().toLowerCase(), 10, 10);
    }

}
