package edu.austral.starship.base.framework;

import processing.core.PApplet;
import processing.core.PImage;

public class ImageLoader {
    private final PApplet applet;

    public ImageLoader(PApplet applet) {this.applet = applet;}

    public PImage load(String fileName) { return applet.loadImage(fileName); }

    public PImage load(String fileName, String extension) { return applet.loadImage(fileName, extension); }
}
