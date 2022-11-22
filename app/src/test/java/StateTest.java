import factory.EntityFactory;
import model.Ship;
import movement.KeyMovement;
import movement.Mover;
import movement.util.Position;
import movement.util.Vector;
import org.junit.Assert;
import org.junit.Test;
import state.GameState;

import java.util.List;

public class StateTest {

    @Test
    public void moveShipTest() {

        GameState gameState = new GameState(800, 800, List.of(), List.of(EntityFactory.createDefaultShipControllerForTesting()));
        GameState updatedGameState = gameState.moveShip("starship-0", KeyMovement.ACCELERATE, 1);

        Mover<Ship> shipMover = updatedGameState.getShipControllers().get(0).getShipMover();
        Assert.assertEquals(10, shipMover.getPosition().getX(), 0.0001);
        Assert.assertEquals(10, shipMover.getPosition().getY(), 0.0001);


    }
}
