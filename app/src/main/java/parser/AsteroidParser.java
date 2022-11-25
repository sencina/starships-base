package parser;

import edu.austral.ingsis.starships.ui.ElementColliderType;
import edu.austral.ingsis.starships.ui.ElementModel;
import edu.austral.ingsis.starships.ui.ImageRef;
import model.Asteroid;
import movement.Mover;

public class AsteroidParser implements EntityParser<Asteroid> {

    @Override
    public ElementModel toElementModel(Mover<Asteroid> entity) {
        return new ElementModel(entity.getId(), entity.getPosition().getX(), entity.getPosition().getY(), 50, 50, entity.getRotationInDegrees(), ElementColliderType.Rectangular, new ImageRef("asteroid", 50, 50));
    }

}
