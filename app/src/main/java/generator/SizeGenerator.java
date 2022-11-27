package generator;
import static config.Constants.*;
public class SizeGenerator {

    public static int generateAsteroidSize() {
        return (int) ((Math.random() * (MAX_ASTEROID_SIZE - MIN_ASTEROID_SIZE)) + MIN_ASTEROID_SIZE);
    }

}
