package edu.austral.visitor;

public interface ElementVisitor {
    void visitSquare(Square square);
    void visitEllipse(Ellipse ellipse);
    void visitTriangle(Triangle triangle);
}
