package config;

public class Constants {

    //GAME CONSTANTS
    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 800;

    //SHIP CONSTANTS
    public static final double SHIP_WIDTH = 70;
    public static final double SHIP_HEIGHT = 70;
    public static final int LIVES = 3;
    public static final int STARTING_SPEED = 10;
    public static final double MAX_SPEED = 50;
    public static final double SPEED_INCREMENT = 10;
    public static final double ROTATION_DEGREES = 15;

    public static final double P1_STARTING_X = 100;
    public static final double P1_STARTING_Y = 100;
    public static final double P2_STARTING_X = 700;
    public static final double P2_STARTING_Y = 700;

    public static final double STARTING_ANGLE = 90;

    //BULLET CONSTANTS
    public static final int BULLETS_PER_SHOT = 1;
    public static final double BULLET_SPEED = 10;
    public static final int BULLET_SIZE = 10;
    public static final int BULLET_DAMAGE = 10;
    public static final int LASER_DAMAGE = 25;
    public static final int ROCKET_DAMAGE = 50;
    public static final int PRISON_MIKE_DAMAGE = 1000;
    public static final String ACTUAL_BULLET = "BULLET";

    //CUSTOM BULLET CONSTANTS
    public static final int CUSTOM_BULLET_SIZE = 10;
    public static final int CUSTOM_BULLET_DAMAGE = 10;
    public static final double CUSTOM_BULLET_SPEED = 10;

    //ASTEROID CONSTANTS
    public static final int ASTEROID_SIZE = 200;
    public static final double ASTEROID_SPEED = 0.5;
    public static final double SPAWN_PROBABILITY = 0.05;


    //KEY CONSTANTS
    public static final String PAUSE_GAME = "P";
    public static final String RESUME_GAME = "R";
    public static final String QUIT_GAME = "Q";
    public static final String SAVE_GAME = "G";
    public static final String LOAD_GAME = "0";
    public static final String ONE_PLAYER = "1";
    public static final String TWO_PLAYER = "2";

}

