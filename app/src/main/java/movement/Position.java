package movement;

public class Position {

    private final double x;
    private final double y;
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Position update(double rotation, double speed) {
        return new PositionCalculator(this).update(rotation, speed);
    }

    private class PositionCalculator{

        private final Position position;

        private PositionCalculator(Position position) {
            this.position = position;
        }

        public Position update(double rotation, double speed) {
            return new Position(position.x + calculateXCoefficient(rotation) * speed, position.y + calculateYCoefficient(rotation) * speed);
        }
        private double calculateYCoefficient(double rotation) {
            return -Math.cos(Math.toRadians(rotation));
        }

        private double calculateXCoefficient(double rotation) {
            return Math.sin(Math.toRadians(rotation));
        }
    }


}
