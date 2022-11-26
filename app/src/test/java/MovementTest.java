import controller.ShipController;
import enums.BulletType;
import factory.EntityFactory;
import model.Weapon;
import movement.Position;
import org.junit.*;

public class MovementTest {

    @Test
    public void testMove() {

        ShipController controller = EntityFactory.createDefaultShipControllerForTesting();
        ShipController controller2 = controller.move();

        Assert.assertEquals(10, controller2.getShipMover().getPosition().getX(), 0.0001);
        Assert.assertEquals(10, controller2.getShipMover().getPosition().getY(), 0.0001);

    }

    @Test
    public void testRotate() {

        ShipController controller = new ShipController(EntityFactory.createDefaultShipMover(new Position(0,0), 90), new Weapon(2, BulletType.BULLET));

        ShipController controller2 = controller.rotate(15);
        System.out.println(controller2.getShipMover().getRotationInDegrees());
        ShipController controller3 = controller2.move();



    }
}
