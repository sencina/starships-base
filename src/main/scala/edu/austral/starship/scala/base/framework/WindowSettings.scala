package edu.austral.starship.scala.base.framework

import processing.core.PApplet

case class WindowSettings(applet: PApplet) {
  def setSize(width: Int, height: Int): WindowSettings = {
    applet.size(width, height)
    this
  }

  def enableHighPixelDensity(): WindowSettings = {
    applet.pixelDensity(2)
    this
  }

  def disableCursor(): WindowSettings = {
    applet.noCursor()
    this
  }

  def enableCursor(): WindowSettings = {
    applet.cursor()
    this
  }

  def fullScreen(): WindowSettings = {
    applet.fullScreen()
    this
  }

  def setFrameRate(rate: Int): WindowSettings = {
    applet.frameRate(rate)
    this
  }
}
