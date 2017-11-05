package edu.austral.visitor;

import edu.austral.util.Collisionable;
import processing.core.PGraphics;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public abstract class Element<T extends Element<T>> implements Collisionable<T>, Visitable {
    private final float x;
    private final float y;
    private final float angle;
    

    public Element(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }


    @Override
    public Shape getShape() {
        final Path2D.Float path = new Path2D.Float();
        path.append(getRelativeShape(), false);

        final AffineTransform transform = new AffineTransform();
        transform.translate(x, y);
        transform.rotate(angle);

        path.transform(transform);

        return path;
    }

    protected abstract Shape getRelativeShape();

    public void draw(PGraphics graphics) {
        graphics.pushMatrix();

        graphics.translate(x, y);
        graphics.rotate(angle);
        relativeDraw(graphics);

        graphics.popMatrix();
    }

    protected abstract void relativeDraw(PGraphics graphics);

    @Override
    public void collisionedWith(T collisionable) {
//        System.out.println("This: " + this + " with " + collisionable);
        collisionable.accept(new CollationVisitor(this));
    }

    protected abstract void collisionedWithSquare(Square square);
    protected abstract void collisionedWithEllipse(Ellipse ellipse);
    protected abstract void collisionedWithTriangle(Triangle triangle);

}
