package edu.austral.visitor;

import edu.austral.util.CollisionEngine;
import processing.core.PApplet;
import scala.collection.JavaConversions;

import java.util.LinkedList;

public class Main extends PApplet {

    public static void main(String args[]) {
        PApplet.main("edu.austral.visitor.Main");
    }

    @Override public void settings() {
        size(500, 500);
    }

    @Override public void draw() {
        clear();

        final LinkedList<Element<?>> elements = new LinkedList<>();

        elements.add(new Ellipse(50, 50, 0));
        elements.add(new Triangle(50, 250, 0));
        elements.add(new Square(250, 175, 0));
        elements.add(new Square(mouseX, mouseY, 0));

        elements.forEach(element -> element.draw(this.g));

        final CollisionEngine collisionEngine = new CollisionEngine();

        //noinspection unchecked
        collisionEngine
                .checkCollisions(JavaConversions.asScalaBuffer(elements).toList());
    }
}
