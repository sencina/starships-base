package edu.austral.dissis.starship.base.framework;

import processing.core.PApplet;
import processing.core.PImage;

import java.net.URL;

import static java.util.Objects.requireNonNull;

public class ImageLoader {
    private final PApplet applet;

    public ImageLoader(PApplet applet) {this.applet = applet;}

    public PImage load(String fileName) {
        final URL url = requireNonNull(getClass().getClassLoader().getResource(fileName));
        return applet.loadImage(url.getPath());
    }
}
