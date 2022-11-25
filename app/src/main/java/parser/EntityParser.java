package parser;

import edu.austral.ingsis.starships.ui.ElementModel;
import model.Collideable;
import movement.Mover;

public interface EntityParser<T extends Collideable<T>> {

    ElementModel toElementModel(Mover<T> entity);
}
