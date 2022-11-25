import factory.EntityFactory;
import factory.StateFactory;
import model.Ship;
import movement.KeyMovement;
import movement.Mover;
import org.junit.Assert;
import org.junit.Test;
import state.GameState;

import java.util.ArrayList;
import java.util.List;

public class StateTest {

    @Test
    public void moveShipTest() {

        GameState gameState = new GameState(800, 800, List.of(), List.of(EntityFactory.createDefaultShipControllerForTesting()), new ArrayList<>());
        GameState updatedGameState = gameState.handleShipAction("starship-0", KeyMovement.ACCELERATE);

        Mover<Ship> shipMover = updatedGameState.getShipControllers().get(0).getShipMover();
        Assert.assertEquals(10, shipMover.getPosition().getX(), 0.0001);
        Assert.assertEquals(10, shipMover.getPosition().getY(), 0.0001);

    }

    @Test
    public void shoot(){
        GameState gameState = StateFactory.createTestGameState();
        GameState updatedGameState = gameState.handleShipAction("starship-0", KeyMovement.SHOOT);
        Assert.assertEquals(0, gameState.getEntities().size());
        Assert.assertEquals(1, updatedGameState.getEntities().size());
    }
}
