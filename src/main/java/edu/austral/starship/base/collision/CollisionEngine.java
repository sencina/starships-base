package edu.austral.starship.base.collision;

import java.awt.*;
import java.awt.geom.Area;
import java.util.List;

public class CollisionEngine<T extends Collisionable<T>> {
    private static <T> T head(List<T> list) { return list.get(0);}

    private static <T> List<T> tail(List<T> list) { return list.subList(1, list.size());}

    void checkCollisions(List<T> collisionables) {
        if (collisionables.isEmpty()) return;

        checkCollisions(head(collisionables), tail(collisionables));
    }

    private boolean testIntersection(Shape shapeA, Shape shapeB) {
        final Area areaA = new Area(shapeA);
        areaA.intersect(new Area(shapeB));
        return !areaA.isEmpty();
    }

    private void checkCollisions(T current, List<T> collisionables) {
        if (collisionables.isEmpty()) return;

        collisionables
            .forEach(collisionable -> {
                if (testIntersection(current.getShape(), collisionable.getShape())) {
                    current.collisionedWith(collisionable);
                    collisionable.collisionedWith(current);
                }
            });

        checkCollisions(head(collisionables), tail(collisionables));
    }
}