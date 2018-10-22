package edu.austral.starship.scala.base.collision

import java.awt.Shape
import java.awt.geom.Area

trait Collisionable[T <: Collisionable[T]] {
  def getShape: Shape

  def collisionedWith(collisionable: T)
}

class CollisionEngine[T <: Collisionable[T]] {
  def checkCollisions(collisionables: List[T]): Unit = {
    if (collisionables.isEmpty) return

    checkCollisions(collisionables.head, collisionables.tail)
  }

  private def testIntersection(shapeA: Shape, shapeB: Shape): Boolean = {
    val areaA = new Area(shapeA)
    areaA.intersect(new Area(shapeB))
    !areaA.isEmpty
  }

  private def checkCollisions(current: T, collisionables: List[T]): Unit = {
    if (collisionables.isEmpty) return

    collisionables
      .foreach(collisionable => {
        if (testIntersection(current.getShape, collisionable.getShape)) {
          current.collisionedWith(collisionable)
          collisionable.collisionedWith(current)
        }
      })

    checkCollisions(collisionables.head, collisionables.tail)
  }
}
