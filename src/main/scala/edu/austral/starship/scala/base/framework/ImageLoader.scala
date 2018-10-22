package edu.austral.starship.scala.base.framework

import processing.core.{PApplet, PImage}

case class ImageLoader(applet: PApplet) {
  def load(fileName: String): PImage = applet.loadImage(fileName)

  def load(fileName: String, extension: String): PImage = applet.loadImage(fileName, extension)
}
