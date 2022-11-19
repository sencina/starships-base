import controller.ShipController;
import enums.BulletType;
import factory.EntityFactory;
import model.Asteroid;
import model.Bullet;
import movement.Mover;
import movement.util.Coordinate;
import movement.util.Vector;
import org.junit.Assert;
import org.junit.Test;

public class ColliderTest {

    @Test
    public void testCollide() {

        ShipController controller1 = EntityFactory.createShip("1", 100);
        ShipController controller2 = EntityFactory.createShip("2", 100);

        ShipController controller3 = controller1.collide(controller2);

        Assert.assertEquals(100, controller3.getHealth());
    }

    @Test
    public void testCollide2() {

        ShipController controller1 = EntityFactory.createShip("1", 100);
        Asteroid asteroid = new Asteroid("2", 100, new Coordinate(0, 0), new Vector(0, 0));

        ShipController controller2 = controller1.collide(asteroid);

        Assert.assertEquals(100, asteroid.getHealth());
        Assert.assertEquals(0, controller2.getHealth());
    }

    @Test
    public void testCollide3(){

        ShipController controller1 = EntityFactory.createShip("1", 100);
        Asteroid asteroid = new Asteroid("2", 100, new Coordinate(0, 0), new Vector(0, 0));

        Mover<Bullet> bullet = controller1.shoot();

        Asteroid asteroid2 = asteroid.collide(bullet.getEntity());

        Assert.assertEquals(90, asteroid2.getHealth());

    }
}
