package edu.austral.visitor;

public class CollationVisitor implements ElementVisitor {
    private final Element<?> element;

    CollationVisitor(Element<?> element) { this.element = element; }

    @Override public void visitSquare(Square square) { element.collisionedWithSquare(square); }

    @Override public void visitEllipse(Ellipse ellipse) { element.collisionedWithEllipse(ellipse); }

    @Override public void visitTriangle(Triangle triangle) { element.collisionedWithTriangle(triangle); }
}
