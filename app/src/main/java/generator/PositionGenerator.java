package generator;

import movement.util.Coordinate;

public class PositionGenerator {

        public static Coordinate generatePosition(double width, double height) {
            return new Coordinate(Math.random() * width,Math.random() * height);
        }

}
