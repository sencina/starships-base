package parser;

import edu.austral.ingsis.starships.ui.ElementColliderType;
import edu.austral.ingsis.starships.ui.ElementModel;
import edu.austral.ingsis.starships.ui.ImageRef;
import model.Ship;
import movement.Mover;
import static config.Constants.*;

public class ShipControllerParser implements EntityParser<Ship> {

    @Override
    public ElementModel toElementModel(Mover<Ship> entity) {
        return new ElementModel(entity.getId(), entity.getPosition().getX(), entity.getPosition().getY(),SHIP_HEIGHT,SHIP_WIDTH, entity.getRotationInDegrees(), ElementColliderType.Triangular,new ImageRef("starship", 70, 70));

    }

}
