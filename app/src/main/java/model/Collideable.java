package model;

import java.util.Optional;

public interface Collideable<T> extends Entity{

    Optional<T> collide(Collideable other);
    int getLives();
    int getDamage();

    Tuple<String, Integer> getPoints();
}
