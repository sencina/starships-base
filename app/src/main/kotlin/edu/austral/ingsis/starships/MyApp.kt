package edu.austral.ingsis.starships

import config.Constants.*
import config.manager.ConfigManager
import controller.ShipController
import edu.austral.ingsis.starships.ui.*
import factory.EntityFactory
import factory.StateFactory
import javafx.application.Application
import javafx.application.Application.launch
import javafx.collections.ObservableMap
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.stage.Stage
import movement.KeyMovement
import movement.Mover
import state.GameState

private var gameState = StateFactory.createNewTwoPlayerGameState()
private val startingShips= gameState.ships.size;
fun main() {
    launch(MyStarships::class.java)
}

class MyStarships() : Application() {
    private val imageResolver = CachedImageResolver(DefaultImageResolver())
    private val facade = ElementsViewFacade(imageResolver)
    private val keyTracker = KeyTracker()

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

    private var rotationIncrement = 0.0
    override fun handle(event: TimePassed) {

        var newShips = ArrayList<ShipController>(gameState.shipControllers)
        val newEntities= ArrayList<Mover<*>>()
        val newIdsToRemove = ArrayList<String>(gameState.idsToRemove)

        newShips = newShips.map { it.move() } as ArrayList<ShipController>

        updateFacadeShips()

        updateFacadeEntities(newEntities, newIdsToRemove)

        removeOutOfBoundsEntities()

        spawnAsteroid(newEntities)

        validateVictory()

        gameState = GameState(gameState.width, gameState.height, newEntities, newShips, newIdsToRemove)
    }

    private fun validateVictory() {
        if(gameState.ships.size < startingShips){
            MyStarships().pause()
            println(gameState.ships[0].id+" won!")
        }
        if(gameState.ships.size == 0){
            MyStarships().pause()
            println("Lost")
        }
    }

    private fun spawnAsteroid(newEntities: ArrayList<Mover<*>>) {
        if (Math.random() < SPAWN_PROBABILITY){
            val asteroidMover = EntityFactory.spawnAsteroid(GAME_WIDTH, GAME_HEIGHT)
            newEntities.add(asteroidMover)
        }
    }

    private fun updateFacadeEntities(newEntities: ArrayList<Mover<*>>, newIdsToRemove: ArrayList<String>) {
        gameState.entities.forEach {
            val newMover = it.move()
            insertMoverInFacade(newMover,0.0, rotationIncrement, false)
            filterEntity(newMover, newEntities, newIdsToRemove)
        }
    }

    private fun insertMoverInFacade(newMover: Mover<*>, angleOffset: Double, rotationIncrement : Double, validatePosition: Boolean) {
        if (elements.containsKey(newMover.id)) {
            elements[newMover.id]?.x?.set(validateX(newMover.position.x, validatePosition))
            elements[newMover.id]?.y?.set(validateY(newMover.position.y, validatePosition))
            elements[newMover.id]?.rotationInDegrees?.set(newMover.rotationInDegrees + angleOffset + rotationIncrement)
        } else {
            elements[newMover.id] = newMover.toElementModel()
        }
    }

    private fun validateY(y: Double, validatePosition: Boolean): Double {
        return if (y < 0 && validatePosition) GAME_HEIGHT + y else if (y > GAME_HEIGHT && validatePosition) y - GAME_HEIGHT else y}

    private fun validateX(x: Double, validatePosition: Boolean): Double {
        return if (x < 0 && validatePosition) GAME_WIDTH + x else if (x > GAME_WIDTH && validatePosition) x - GAME_WIDTH else x
    }

    private fun filterEntity(newMover: Mover<*>?, newEntities: java.util.ArrayList<Mover<*>>, newIdsToRemove: ArrayList<String>) {
        if (newMover != null) {
            if (newMover.position.x <= GAME_WIDTH + OFFSET && newMover.position.y <= GAME_HEIGHT + OFFSET && newMover.position.x >= -OFFSET && newMover.position.y >= -OFFSET
            ) {
                newEntities.add(newMover)
            } else {
                newIdsToRemove.add(newMover.id)
            }
        }
    }

    private fun removeOutOfBoundsEntities() {
        gameState.idsToRemove.forEach {
            elements.remove(it)
        }
    }

    private fun updateFacadeShips() {
        gameState.ships.forEach {
            insertMoverInFacade(it.shipMover, 180.0, 0.0, true)
        }
    }
}

class MyCollisionListener() : EventListener<Collision> {
    override fun handle(event: Collision) {
        println("${event.element1Id} ${event.element2Id}")
        gameState = gameState.collideEntities(event.element1Id, event.element2Id);
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
            keyCodeOf(PAUSE_GAME) -> pauseGame()
            keyCodeOf(RESUME_GAME) -> resumeGame()
            keyCodeOf(SAVE_GAME) -> ConfigManager.saveState(gameState)
            else -> {}
        }
    }

    private fun pauseGame() {
        facade.stop()
        gameState = gameState.changeState()
    }

    private fun resumeGame() {
        facade.start()
        gameState = gameState.changeState()
    }

    private fun keyCodeOf(string: String): Any {
        return KeyCode.valueOf(string)
    }

}