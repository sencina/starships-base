package movement;

public class Position {

    private final double x;
    private final double y;
    //TODO implement time
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

    public Position move(double rotation, double speed) {
        return new Position(x + calculateXCoefficient(rotation) * speed, y + calculateYCoefficient(rotation) * speed);
    }

    private double calculateYCoefficient(double rotation) {
        return -Math.cos(Math.toRadians(rotation));
    }

    private double calculateXCoefficient(double rotation) {
        return Math.sin(Math.toRadians(rotation));
    }
}
