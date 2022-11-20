import model.Ship;
import movement.KeyMovement;
import movement.Mover;
import movement.util.Coordinate;
import movement.util.Vector;
import org.junit.Assert;
import org.junit.Test;
import state.GameState;

import java.util.List;

public class StateTest {

    @Test
    public void moveShipTest() {

        GameState state = new GameState(800,800, List.of(new Mover(new Ship("1", 100), new Coordinate(0, 0), new Vector(0, 1), 10), new Mover(new Ship("2", 100), new Coordinate(0, 0), new Vector(0, 1), 10)));

        GameState newState = state.moveShip("1", KeyMovement.ACCELERATE,1);

        Mover controller = newState.getEntities().get(1);

        Assert.assertEquals(0, controller.getPosition().getX(), 0.0001);
        Assert.assertEquals(10, controller.getPosition().getY(), 0.0001);

    }
}
