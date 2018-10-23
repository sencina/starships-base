package edu.austral.starship.base.framework;

import edu.austral.starship.CustomGameFramework;
import processing.core.PApplet;
import processing.event.KeyEvent;

import java.util.HashSet;
import java.util.Set;

public class GameManager extends PApplet {
    private final GameFramework gameFramework = new CustomGameFramework();
    private final Set<Integer> keySet = new HashSet<>();

    public void settings() {
        gameFramework.setup(new WindowSettings(this), new ImageLoader(this));
    }

    public void draw() {
        clear();

        final float timeSinceLastFrame = (frameRate / 60) * 100;
        gameFramework.draw(g, timeSinceLastFrame, keySet);
    }

    public void keyPressed(KeyEvent event) {
        keySet.add(event.getKeyCode());

        gameFramework.keyPressed(event);
    }

    public void keyReleased(KeyEvent event) {
        keySet.remove(event.getKeyCode());

        gameFramework.keyReleased(event);
    }
}
