package edu.austral.ingsis.starships.adapter

import config.Constants
import controller.ShipController
import factory.EntityFactory
import movement.KeyMovement
import movement.Mover
import state.GameState

class Adapter(val gameState: GameState, private val spawnProbs: Double) {

    fun moveEntities(): Adapter {

        var newShips = ArrayList<ShipController>(gameState.shipControllers)
        val newEntities = ArrayList<Mover<*>>()
        val newIdsToRemove = ArrayList<String>(gameState.idsToRemove)

        newShips = newShips.map { it.move() } as ArrayList<ShipController>

        moveAndFilterEntities(newEntities, newIdsToRemove)

        spawnAsteroid(newEntities)

        return Adapter(GameState(gameState.width,gameState.height,newEntities,newShips,newIdsToRemove,gameState.points,gameState.isPaused), spawnProbs)
    }

    private fun moveAndFilterEntities(newEntities: ArrayList<Mover<*>>, newIdsToRemove: ArrayList<String>) {
        gameState.entities.forEach {
            val newMover = it.move()
            filterEntity(newMover, newEntities, newIdsToRemove)
        }
    }

    private fun filterEntity(newMover: Mover<*>?, newEntities: java.util.ArrayList<Mover<*>>, newIdsToRemove: ArrayList<String>) {
        if (newMover != null) {
            if (newMover.position.x <= gameState.width + Constants.OFFSET && newMover.position.y <= gameState.height + Constants.OFFSET && newMover.position.x >= -Constants.OFFSET && newMover.position.y >= -Constants.OFFSET
            ) {
                newEntities.add(newMover)
            } else {
                newIdsToRemove.add(newMover.id)
            }
        }
    }

    private fun spawnAsteroid(newEntities: ArrayList<Mover<*>>) {
        if (Math.random() < spawnProbs && !gameState.isPaused) {
            val asteroidMover = EntityFactory.spawnAsteroid(gameState.width, gameState.height)
            newEntities.add(asteroidMover)
        }
    }

    fun handleCollision(id1: String, id2: String):Adapter{
        return Adapter(gameState.collideEntities(id1,id2),spawnProbs)
    }

    fun changeState():Adapter{
        return Adapter(gameState.changeState(),spawnProbs)
    }
    fun handleShipAction(id: String, movement: KeyMovement):Adapter{
        return Adapter(gameState.handleShipAction(id,movement),spawnProbs)
    }

}