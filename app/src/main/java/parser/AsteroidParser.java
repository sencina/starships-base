package parser;

import edu.austral.ingsis.starships.ui.ElementColliderType;
import edu.austral.ingsis.starships.ui.ElementModel;
import edu.austral.ingsis.starships.ui.ImageRef;
import model.Asteroid;
import movement.Mover;
import static config.Constants.*;

public class AsteroidParser implements EntityParser<Asteroid> {

    @Override
    public ElementModel toElementModel(Mover<Asteroid> entity) {
        return new ElementModel(entity.getId(), entity.getPosition().getX(), entity.getPosition().getY(), getAsteroidSize(entity.getEntity()), getAsteroidSize(entity.getEntity()), entity.getRotationInDegrees(), ElementColliderType.Rectangular, new ImageRef("asteroid", getAsteroidSize(entity.getEntity()), getAsteroidSize(entity.getEntity())));
    }

    private int getAsteroidSize(Asteroid asteroid) {
        return asteroid.getLives();
    }

}
