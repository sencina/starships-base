package edu.austral.dissis.starship.base.framework;

import processing.core.PGraphics;
import processing.event.KeyEvent;

import java.util.Set;

public interface GameFramework {
    void setup(WindowSettings windowsSettings, ImageLoader imageLoader);

    void draw(PGraphics graphics, float timeSinceLastDraw, Set<Integer> keySet);

    void keyPressed(KeyEvent event);

    void keyReleased(KeyEvent event);
}
