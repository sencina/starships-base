package edu.austral.visitor;

import processing.core.PGraphics;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Ellipse extends Element<Ellipse> {

    public Ellipse(float x, float y, float angle) { super(x, y, angle); }

    @Override protected Shape getRelativeShape() { return new Ellipse2D.Float(-10, -5, 20, 10); }

    @Override protected void relativeDraw(PGraphics graphics) {
        graphics.fill(0, 0, 255);
        graphics.ellipse(-10, -5, 20, 10);
    }

    @Override protected void collisionedWithSquare(Square square) { }

    @Override protected void collisionedWithEllipse(Ellipse square) { }

    @Override protected void collisionedWithTriangle(Triangle square) { }

    @Override public void accept(ElementVisitor visitor) { visitor.visitEllipse(this); }
}
