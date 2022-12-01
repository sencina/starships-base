package model;

import java.util.Optional;

public interface Collideable extends Entity{

    Optional<Collideable> collide(Collideable other);
    int getLives();
    int getDamage();

    Tuple<String, Integer> getPoints();
}
