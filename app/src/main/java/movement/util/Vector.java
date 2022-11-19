package movement.util;

public class Vector {

    private final double x;
    private final double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(double angleInDegrees){
        this(Math.cos(Math.toRadians(angleInDegrees)), Math.sin(Math.toRadians(angleInDegrees)));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngleInDegrees(){
        return Math.toDegrees(Math.atan2(y,x));
    }

    public Vector add(Vector vector) {
        return new Vector(x + vector.getX(), y + vector.getY());
    }

    public Vector subtract(Vector vector) {
        return new Vector(x - vector.getX(), y - vector.getY());
    }

    public Vector rotate(double degrees) {
        double angleInRadians = Math.toRadians(degrees);
        double newX = x * Math.cos(angleInRadians) - y * Math.sin(angleInRadians);
        double newY = x * Math.sin(angleInRadians) + y * Math.cos(angleInRadians);
        return new Vector(newX, newY);
    }
}
