package model;

public interface Collideable<T> {

    T collide(Collideable other);
    int getHealth();

    int getDamage();
}
