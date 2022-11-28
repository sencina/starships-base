package parser;

import edu.austral.ingsis.starships.ui.ElementModel;
import movement.Mover;

public class ModelToUIParser {

    public static ElementModel parseModelToUIModel(Mover mover) {
        return switch (mover.getEntityType()){
            case STARSHIP -> new ShipControllerParser().toElementModel(mover);
            case BULLET -> new BulletParser().toElementModel(mover);
            case ASTEROID -> new AsteroidParser().toElementModel(mover);
        };
    }
}
