package edu.austral.visitor;

public interface Visitable {
    void accept(ElementVisitor visitor);
}
