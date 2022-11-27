package parser;

import edu.austral.ingsis.starships.ui.ElementModel;
import edu.austral.ingsis.starships.ui.ImageRef;
import model.Asteroid;
import movement.Mover;
import static config.Constants.*;

public class AsteroidParser implements EntityParser<Asteroid> {

    @Override
    public ElementModel toElementModel(Mover<Asteroid> entity) {
        return new ElementModel(entity.getId(), entity.getPosition().getX(), entity.getPosition().getY(), getAsteroidSize(entity.getEntity()), getAsteroidSize(entity.getEntity()), entity.getRotationInDegrees(), ASTEROID_COLLIDER_TYPE, new ImageRef("asteroid",entity.getEntity().getLives(),entity.getEntity().getLives()));
    }

    private int getAsteroidSize(Asteroid asteroid) {
        return asteroid.getLives();
    }

}
