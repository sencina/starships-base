package edu.austral.visitor;

import processing.core.PGraphics;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Square extends Element<Square> {

    public Square(float x, float y, float angle) { super(x, y, angle); }

    @Override protected Shape getRelativeShape() { return new Rectangle2D.Float(-10, -5, 20, 10); }

    @Override protected void relativeDraw(PGraphics graphics) {
        graphics.fill(0, 255, 0);
        graphics.rect(-10, -5, 20, 10);
    }

    @Override protected void collisionedWithSquare(Square square) {
        System.out.println("I crashed against a square!");
    }

    @Override protected void collisionedWithEllipse(Ellipse ellipse) {
        System.out.println("I crashed against a ellipse!");
    }

    @Override protected void collisionedWithTriangle(Triangle triangle) {
        System.out.println("I crashed against a triangle!");
    }

    @Override public void accept(ElementVisitor visitor) { visitor.visitSquare(this); }
}
