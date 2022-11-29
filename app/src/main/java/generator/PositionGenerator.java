package generator;

import model.Tuple;
import movement.Position;

public class PositionGenerator {

    public static Tuple<Position, Double> generateAsteroidPosition(double gameWidth, double gameHeight) {
        Position position = generateRandoPosition(gameWidth, gameHeight);
        double angle = generateAngleFromPosition(position, gameWidth, gameHeight);
        return new Tuple<>(position, angle);
    }

    private static double generateAngleFromPosition(Position position, double gameWidth, double gameHeight) {
        if (position.getX() == 0) {
            return randomAngle(0, 180);
        } else if (position.getX() == gameWidth) {
            return randomAngle(180, 360);
        } else if (position.getY() == 0) {
            return randomAngle(90, 270);
        } else {
            return randomAngle(270, 450);
        }
    }

    private static double randomAngle(double from, double to) {
        return Math.random() * (to - from) + from;
    }

    private static Position generateRandoPosition(double gameWidth, double gameHeight) {
        double random = Math.random();
        if (random < 0.25) {
            return new Position(0, Math.random() * gameHeight);
        } else if (random < 0.5) {
            return new Position(gameWidth, Math.random() * gameHeight);
        } else if (random < 0.75) {
            return new Position(Math.random() * gameWidth, 0);
        } else {
            return new Position(Math.random() * gameWidth, gameHeight);
        }
    }


}
