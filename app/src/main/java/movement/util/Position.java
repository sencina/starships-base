package movement.util;

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

    public Position move(Vector vector, double speed) {
        return new Position(x + (int) vector.getX() * speed, y + (int) vector.getY() * speed);
    }
}
