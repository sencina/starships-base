package edu.austral.ingsis.starships

import config.reader.ConfigManager
import controller.ShipController
import edu.austral.ingsis.starships.ui.*
import factory.StateFactory
import javafx.application.Application
import javafx.application.Application.launch
import javafx.beans.Observable
import javafx.collections.ObservableMap
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.stage.Stage
import movement.KeyMovement
import movement.Mover
import state.GameState

private var gameState = ConfigManager.readState()//StateFactory.createTestGameState()
fun main() {
    launch(MyStarships::class.java)
}

class MyStarships() : Application() {
    private val imageResolver = CachedImageResolver(DefaultImageResolver())
    private val facade = ElementsViewFacade(imageResolver)
    private val keyTracker = KeyTracker()

    companion object {
        val STARSHIP_IMAGE_REF = ImageRef("starship", 70.0, 70.0)
    }

    override fun start(primaryStage: Stage) {

        addElementModels()
        addListeners()
        setScenes(primaryStage)
        startGame(primaryStage)

    }

    private fun startGame(primaryStage: Stage) {
        facade.start()
        keyTracker.start()
        primaryStage.show()
    }

    private fun setScenes(primaryStage: Stage) {
        val scene = Scene(facade.view)
        keyTracker.scene = scene

        primaryStage.scene = scene
        primaryStage.height = gameState.height
        primaryStage.width = gameState.width

    }

    private fun addListeners() {
        facade.timeListenable.addEventListener(MyTimeListener(facade.elements))
        facade.collisionsListenable.addEventListener(MyCollisionListener())
        keyTracker.keyPressedListenable.addEventListener(MyKeyPressedListener(gameState.shipControllers,facade))
    }

    private fun addElementModels() {

        gameState.ships.forEach { ship ->
            facade.elements[ship.id] = ship.toElementModel()
        }

        gameState.entities.forEach { entity ->
            facade.elements[entity.id] = entity.toElementModel()
        }

    }

    fun pause(){
        facade.stop()
    }

    override fun stop() {
        facade.stop()
        keyTracker.stop()
    }
}

class MyTimeListener(private val elements: ObservableMap<String, ElementModel>) : EventListener<TimePassed> {
    override fun handle(event: TimePassed) {

        val newShips = ArrayList<ShipController>(gameState.shipControllers)
        val newEntities= ArrayList<Mover<*>>()

        newShips.map { it.move() }

        updateFacadeShips()

        gameState.entities.forEach {

            val newMover = it.move()
            val facadeMover = newMover.toElementModel()

            if (elements.containsKey(it.id)) {
                elements[it.id]?.x?.set(newMover.position.x)
                elements[it.id]?.y?.set(newMover.position.y)
                elements[it.id]?.rotationInDegrees?.set(newMover.getRotationInDegrees() + 270)
            } else {
                elements[it.id] = facadeMover
            }

            newEntities.add(newMover)
        }

        removeOutOfBoundsEntities()

        gameState = GameState(gameState.width, gameState.height, newEntities, newShips,ArrayList())
    }

    private fun removeOutOfBoundsEntities() {
        gameState.idsToRemove.forEach {
            elements.remove(it)
        }
    }

    private fun updateFacadeShips() {
        gameState.ships.forEach() {
            if (elements.containsKey(it.id)) {
                elements[it.id]?.x?.set(it.position.x)
                elements[it.id]?.y?.set(it.position.y)
                elements[it.id]?.rotationInDegrees?.set(it.getRotationInDegrees() + 270)
            }
        }
    }
}

class MyCollisionListener() : EventListener<Collision> {
    override fun handle(event: Collision) {
        println("${event.element1Id} ${event.element2Id}")
    }

}

class MyKeyPressedListener(private val controllers: List<ShipController>, private var facade: ElementsViewFacade): EventListener<KeyPressed> {

    private var keyBindMap: Map<String,Map<KeyMovement,KeyCode>> = ConfigManager.readBindings()
    override fun handle(event: KeyPressed) {
        val key = event.key

        handlePauseResume(key)

        controllers.forEach { controller ->
            keyBindMap[controller.id]?.forEach { (movement, keyCode) ->
                if(key == keyCode){
                   gameState = gameState.handleShipAction(controller.id, movement)
                }
            }
        }


    }

    private fun handlePauseResume(key: KeyCode) {
        when(key){
            KeyCode.P -> facade.stop()
            KeyCode.R -> facade.start()
            KeyCode.G -> ConfigManager.saveState(gameState)
            else -> {}
        }
    }

}