package generator;

import static config.Constants.MAX_ASTEROID_SPEED;
import static config.Constants.MIN_ASTEROID_SPEED;

public class SpeedGenerator {

    public static double generateAsteroidSpeed() {
        return (Math.random() * (MAX_ASTEROID_SPEED - MIN_ASTEROID_SPEED)) + MIN_ASTEROID_SPEED;
    }

}
