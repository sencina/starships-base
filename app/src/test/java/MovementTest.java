import controller.ShipController;
import enums.BulletType;
import model.Ship;
import model.Weapon;
import movement.Mover;
import movement.util.Coordinate;
import movement.util.Vector;
import org.junit.*;

public class MovementTest {


    @org.junit.Test
    public void movementTest(){
        ShipController controller1 = new ShipController(new Mover<>(new Ship("1", 100), new Coordinate(0, 0), new Vector(0, 1), 10), new Weapon(10, BulletType.BULLET));
        ShipController movedController = controller1.move(1);

        Assert.assertEquals(0, movedController.getPosition().getX(), 0.0001);
        Assert.assertEquals(10, movedController.getPosition().getY(), 0.0001);
    }

    @org.junit.Test
    public void movementTest2(){
        ShipController controller1 = new ShipController(new Mover<>(new Ship("1", 100), new Coordinate(0, 0), new Vector(1, 1), 10), new Weapon(10, BulletType.BULLET));
        ShipController movedController = controller1.move(1);

        Assert.assertEquals(10, movedController.getPosition().getX(), 0.001);
        Assert.assertEquals(10, movedController.getPosition().getY(), 0.001);
    }
}
