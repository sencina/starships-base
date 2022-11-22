import controller.ShipController;
import factory.EntityFactory;
import model.Asteroid;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class ColliderTest {

    @Test
    public void testCollide() {

        ShipController controller1 = EntityFactory.createDefaultShipControllerForTesting();
        ShipController controller2 = EntityFactory.createDefaultShipControllerForTesting();

        ShipController controller3 = controller1.collide(controller2).get();

        Assert.assertEquals(3, controller3.getLives());
    }

    @Test
    public void testCollide2() {

        ShipController controller1 = EntityFactory.createDefaultShipControllerForTesting();
        Asteroid asteroid = new Asteroid("2", 100);

        Optional<ShipController> controller2 = controller1.collide(asteroid);

        ShipController controller3 = controller2.get();

        Assert.assertEquals(100, asteroid.getLives());
        Assert.assertEquals(2, controller3.getLives());
    }

    @Test
    public void deathTest() {

        ShipController controller1 = EntityFactory.createDefaultShipControllerForTesting();
        Asteroid asteroid = new Asteroid("2", 100);

        Optional<ShipController> controller2 = controller1.collide(asteroid);
        Optional<ShipController> controller3 = controller2.get().collide(asteroid);
        Optional<ShipController> controller4 = controller3.get().collide(asteroid);

        Assert.assertEquals(100, asteroid.getLives());
        Assert.assertTrue( controller4.isEmpty());
    }

    @Test
    public void asteroidCollisionTest(){

        Asteroid asteroid1 = new Asteroid("1", 100);
        Asteroid asteroid2 = new Asteroid("2", 100);

        Optional<Asteroid> asteroid3 = asteroid1.collide(asteroid2);

        Assert.assertEquals(100, asteroid3.get().getLives());
    }
}
