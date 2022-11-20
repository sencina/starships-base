package parser;

import controller.ShipController;
import edu.austral.ingsis.starships.ui.ElementColliderType;
import edu.austral.ingsis.starships.ui.ElementModel;
import edu.austral.ingsis.starships.ui.ImageRef;
import movement.Mover;

public class ModelToElementModelParser {

    public static ElementModel parse(Mover<ShipController> controller){
        return new ElementModel(controller.getId(),controller.getPosition().getX(),controller.getPosition().getY(),60,70,controller.getVector().getAngleInDegrees(), ElementColliderType.Triangular, new ImageRef("spacehip",60,70));
    }
}
