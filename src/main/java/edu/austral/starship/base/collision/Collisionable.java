package edu.austral.starship.base.collision;

import java.awt.*;

public interface Collisionable<T extends Collisionable<T>> {
    Shape getShape();

    void collisionedWith(T collisionable);
}
