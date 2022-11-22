import controller.ShipController;
import factory.EntityFactory;
import org.junit.*;

public class MovementTest {

    @Test
    public void testMove() {

        ShipController controller = EntityFactory.createDefaultShipControllerForTesting();
        ShipController controller2 = controller.move(1);

        Assert.assertEquals(10, controller2.getShipMover().getPosition().getX(), 0.0001);
        Assert.assertEquals(10, controller2.getShipMover().getPosition().getY(), 0.0001);


    }
}
