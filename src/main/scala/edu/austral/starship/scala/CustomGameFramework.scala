package edu.austral.starship.scala

import edu.austral.starship.scala.base.framework.{GameFramework, ImageLoader, WindowSettings}
import processing.core.PGraphics
import processing.event.KeyEvent

object CustomGameFramework extends GameFramework {
  override def setup(windowsSettings: WindowSettings, imageLoader: ImageLoader): Unit = {
    windowsSettings
      .setSize(500, 500)
  }

  override def draw(graphics: PGraphics, timeSinceLastDraw: Float, keySet: Set[Int]): Unit = {

  }

  override def keyPressed(event: KeyEvent): Unit = {

  }

  override def keyReleased(event: KeyEvent): Unit = {

  }
}
