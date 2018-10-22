package edu.austral.starship.scala.base.vector

import java.lang.Math.{atan2, cos, sin}

import scala.language.postfixOps

case class Vector2(x: Float, y: Float) {
  lazy val module: Float = Math.pow(Math.pow(x, 2) + Math.pow(y, 2), 0.5).toFloat
  lazy val unitary: Vector2 = Vector2(x / module, y / module)
  lazy val angle: Float = atan2(y, x) - atan2(0, 1) toFloat

  def +(other: Vector2): Vector2 = Vector2(x + other.x, y + other.y)

  def -(other: Vector2): Vector2 = Vector2(x - other.x, y - other.y)

  def *(scalar: Float): Vector2 = Vector2(x * scalar, y * scalar)

  def rotate(angle: Float): Vector2 = Vector2(x * cos(angle) - y * sin(angle) toFloat, x * sin(angle) + y * cos(angle) toFloat)
}

object Vector2 {
  def fromModule(module: Float, angle: Float) = Vector2(module * cos(angle) toFloat, module * sin(angle) toFloat)
}

