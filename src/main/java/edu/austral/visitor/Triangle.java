package edu.austral.visitor;

import processing.core.PGraphics;

import java.awt.*;

public class Triangle extends Element<Triangle> {
    private static final int[] xCoordinates = {10, -10, -10};
    private static final int[] yCoordinates = {0, -10, 10};

    public Triangle(float x, float y, float angle) { super(x, y, angle); }

    @Override protected Shape getRelativeShape() { return new Polygon(xCoordinates, yCoordinates, 3); }

    @Override protected void relativeDraw(PGraphics graphics) {
        graphics.fill(0, 0, 255);
        graphics.triangle(
                xCoordinates[0], yCoordinates[0],
                xCoordinates[1], yCoordinates[1],
                xCoordinates[2], yCoordinates[2]
        );
    }

    @Override protected void collisionedWithSquare(Square square) { }

    @Override protected void collisionedWithEllipse(Ellipse square) { }

    @Override protected void collisionedWithTriangle(Triangle square) { }

    @Override public void accept(ElementVisitor visitor) { visitor.visitTriangle(this); }
}
