package config;

import edu.austral.ingsis.starships.ui.ElementColliderType;
import edu.austral.ingsis.starships.ui.ImageRef;

public class Constants {

    //GAME CONSTANTS
    public static final double GAME_WIDTH = 1000;
    public static final double GAME_HEIGHT = 1000;

    //SHIP CONSTANTS
    public static final double SHIP_WIDTH = 70;
    public static final double SHIP_HEIGHT = 70;
    public static final int LIVES = 3;
    public static final int STARTING_SPEED = 0;
    public static final double MAX_SPEED = 3;
    public static final double SPEED_INCREMENT = 1;
    public static final double ROTATION_DEGREES = 15;
    public static final double STARTING_ANGLE = 90;

    //BULLET CONSTANTS
    public static final int BULLETS_PER_SHOT = 1;
    public static final double BULLET_SPEED = 10;
    public static final int BULLET_SIZE = 10;
    public static final int BULLET_DAMAGE = 10;
    public static final int LASER_DAMAGE = 25;
    public static final int LASER_SIZE = 10;
    public static final int ROCKET_DAMAGE = 50;
    public static final int ROCKET_SIZE = 20;
    public static final int PRISON_MIKE_DAMAGE = 1000;
    public static final int PRISON_MIKE_SIZE = 50;
    public static final String ACTUAL_BULLET = "BULLET";

    //CUSTOM BULLET CONSTANTS
    public static final int CUSTOM_BULLET_SIZE = 10;
    public static final int CUSTOM_BULLET_DAMAGE = 10;
    public static final double CUSTOM_BULLET_SPEED = 10;

    //ASTEROID CONSTANTS
    public static final int MAX_ASTEROID_SIZE = 200;
    public static final int MIN_ASTEROID_SIZE = 50;

    public static final double MAX_ASTEROID_SPEED = 2;
    public static final double MIN_ASTEROID_SPEED = 0.5;
    public static final double SPAWN_PROBABILITY = 0.002;

    //KEY CONSTANTS
    public static final String PAUSE_GAME = "P";
    public static final String RESUME_GAME = "R";
    public static final String SAVE_GAME = "G";


    //IMAGE REFERENCES

    public static final ImageRef STARSHIP_IMAGE_REF = new ImageRef("starship", SHIP_HEIGHT, SHIP_WIDTH);
    public static final ImageRef BULLET_IMAGE_REF = new ImageRef("bullet", BULLET_SIZE, BULLET_SIZE);
    public static final ImageRef CUSTOM_BULLET_IMAGE_REF = new ImageRef("custom", CUSTOM_BULLET_SIZE, CUSTOM_BULLET_SIZE);
    public static final ImageRef LASER_IMAGE_REF = new ImageRef("laser", LASER_SIZE, LASER_SIZE);
    public static final ImageRef ROCKET_IMAGE_REF = new ImageRef("rocket", ROCKET_SIZE, ROCKET_SIZE);
    public static final ImageRef PRISON_MIKE_IMAGE_REF = new ImageRef("prison_mike", PRISON_MIKE_SIZE, PRISON_MIKE_SIZE);
    public static final ElementColliderType ASTEROID_COLLIDER_TYPE = ElementColliderType.Elliptical;
    public static final ElementColliderType SHIP_COLLIDER_TYPE = ElementColliderType.Triangular;
    public static final ElementColliderType BULLET_COLLIDER_TYPE = ElementColliderType.Rectangular;
    public static final double OFFSET = MAX_ASTEROID_SIZE;

    public final static String STATE_PATH = System.getProperty("user.dir") + "/app/src/main/java/config/state.json";
    public final static String KEYS_PATH = System.getProperty("user.dir") + "/app/src/main/java/config/playerKeys.json";
}

