package generator;

import movement.Position;

public class PositionGenerator {

        public static Position generatePosition(double width, double height) {
            return new Position(Math.random() * width,Math.random() * height);
        }

}
