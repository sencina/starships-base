package generator;

import movement.util.Vector;

public class VectorGenerator {

    public static Vector generateVector() {
        return new Vector(Math.random() * 360);
    }

}
