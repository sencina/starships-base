package movement.util;

public class Coordinate {

    private final double x;
    private final double y;
    //TODO implement time
    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Coordinate move(Vector vector, double time, double speed) {
        return new Coordinate(x + (int) vector.getX() * speed, y + (int) vector.getY() * speed);
    }
}
