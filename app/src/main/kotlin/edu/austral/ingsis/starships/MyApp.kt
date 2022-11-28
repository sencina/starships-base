package edu.austral.ingsis.starships

import config.Constants.*
import config.manager.ConfigManager
import controller.ShipController
import edu.austral.ingsis.starships.ui.*
import factory.EntityFactory
import factory.StateFactory
import javafx.application.Application
import javafx.application.Application.launch
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.stage.Stage
import movement.KeyMovement
import movement.Mover
import state.GameState
import kotlin.system.exitProcess
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.geometry.Pos
import javafx.scene.Cursor
import parser.ModelToUIParser

private var gameState = StateFactory.createEmptyGame()
private var startingShips= -1;
fun main() {
    launch(MyStarships::class.java)
}

class MyStarships() : Application() {
    private val imageResolver = CachedImageResolver(DefaultImageResolver())
    private val facade = ElementsViewFacade(imageResolver)
    private val keyTracker = KeyTracker()

    override fun start(primaryStage: Stage) {
        val lives = StackPane()
        var livesList= addCssToLives(Label(LIVES.toString()), Label(LIVES.toString()))
        var divList = addCssToDivs(HBox(50.0), HBox(50.0), livesList[0], livesList[1])
        lives.children.addAll(divList)

        val pane=StackPane()
        val layout = VBox(100.0)

        val scene = setGameScene(pane, lives, layout, primaryStage)

        //Initial Menu
        initializeMenu(layout, scene, pane)

        addListeners()

        startGame(primaryStage)
    }

    private fun addListeners() {
        facade.collisionsListenable.addEventListener(object : EventListener<Collision> {
            override fun handle(event: Collision) {
                println("${event.element1Id} ${event.element2Id}")
                gameState = gameState.collideEntities(event.element1Id, event.element2Id);
            }
        })

        facade.timeListenable.addEventListener(object : EventListener<TimePassed> {

            override fun handle(event: TimePassed) {

                var newShips = ArrayList<ShipController>(gameState.shipControllers)
                val newEntities = ArrayList<Mover<*>>()
                val newIdsToRemove = ArrayList<String>(gameState.idsToRemove)

                newShips = newShips.map { it.move() } as ArrayList<ShipController>

                updateFacadeShips()

                updateFacadeEntities(newEntities, newIdsToRemove)

                removeOutOfBoundsEntities()

                spawnAsteroid(newEntities)

                gameState = GameState(
                    gameState.width, gameState.height, newEntities, newShips, newIdsToRemove, gameState.points,
                    gameState.isPaused
                )
            }

            private fun validateVictory() {
                if (gameState.ships.size < startingShips) {
                    MyStarships().pause()
                    //println(gameState.ships[0].id+" won!")
                    facade.elements.clear()
                    //addMessagetoScreen(gameState.ships[0].id+" won!")
                    exitProcess(0)
                }
                if (gameState.ships.size == 0) {
                    MyStarships().pause()
                    println("Lost")
                    facade.elements.clear()
                }
            }

            private fun spawnAsteroid(newEntities: ArrayList<Mover<*>>) {
                if (Math.random() < SPAWN_PROBABILITY && !gameState.isPaused) {
                    val asteroidMover = EntityFactory.spawnAsteroid(GAME_WIDTH, GAME_HEIGHT)
                    newEntities.add(asteroidMover)
                }
            }

            private fun updateFacadeEntities(newEntities: ArrayList<Mover<*>>, newIdsToRemove: ArrayList<String>) {
                gameState.entities.forEach {
                    val newMover = it.move()
                    insertMoverInFacade(newMover, 0.0, false)
                    filterEntity(newMover, newEntities, newIdsToRemove)
                }
            }

            private fun insertMoverInFacade(newMover: Mover<*>, angleOffset: Double, validatePosition: Boolean) {
                if (facade.elements.containsKey(newMover.id)) {
                    facade.elements[newMover.id]?.x?.set(validateX(newMover.position.x, validatePosition))
                    facade.elements[newMover.id]?.y?.set(validateY(newMover.position.y, validatePosition))
                    facade.elements[newMover.id]?.rotationInDegrees?.set(newMover.rotationInDegrees + angleOffset)
                } else {
                    facade.elements[newMover.id] = ModelToUIParser.parseModelToUIModel(newMover)
                }
            }

            private fun validateY(y: Double, validatePosition: Boolean): Double {
                return if (y < 0 && validatePosition) GAME_HEIGHT + y else if (y > GAME_HEIGHT && validatePosition) y - GAME_HEIGHT else y
            }

            private fun validateX(x: Double, validatePosition: Boolean): Double {
                return if (x < 0 && validatePosition) GAME_WIDTH + x else if (x > GAME_WIDTH && validatePosition) x - GAME_WIDTH else x
            }

            private fun filterEntity(
                newMover: Mover<*>?,
                newEntities: java.util.ArrayList<Mover<*>>,
                newIdsToRemove: ArrayList<String>
            ) {
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
                    facade.elements.remove(it)
                }
            }

            private fun updateFacadeShips() {
                gameState.ships.forEach {
                    insertMoverInFacade(it.shipMover, 180.0, true)
                }
            }
        })

        keyTracker.keyPressedListenable.addEventListener(object : EventListener<KeyPressed> {

            private var keyBindMap: Map<String, Map<KeyMovement, KeyCode>> = ConfigManager.readBindings()
            override fun handle(event: KeyPressed) {
                val key = event.key

                handlePauseResume(key)

                gameState.ships.forEach { controller ->
                    keyBindMap[controller.id]?.forEach { (movement, keyCode) ->
                        if (key == keyCode) {
                            gameState = gameState.handleShipAction(controller.id, movement)
                        }
                    }
                }


            }

            private fun handlePauseResume(key: KeyCode) {
                when (key) {
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
        })
    }

    private fun startGame(primaryStage: Stage) {
        facade.start()
        keyTracker.start()
        primaryStage.show()
    }

    private fun setGameScene(
        pane: StackPane,
        lives: StackPane,
        layout: VBox,
        primaryStage: Stage
    ): Scene {
        val root = facade.view
        pane.children.addAll(root, lives)
        root.id = "game"
        val scene = Scene(layout)
        keyTracker.scene = scene
        scene.stylesheets.add(this::class.java.classLoader.getResource("style.css")?.toString())
        scene.stylesheets.add("https://fonts.googleapis.com/css2?family=VT323&display=swap")
        primaryStage.scene = scene
        primaryStage.height = GAME_HEIGHT
        primaryStage.width = GAME_WIDTH
        return scene
    }

    private fun initializeMenu(
        layout: VBox,
        scene: Scene,
        pane: StackPane
    ) {
        addCssToLayout(layout)

        val name = Label("Dwightsteroids")
        addCssToLabel(name)

        val options = HBox(100.0)
        options.alignment = Pos.CENTER

        val onePlayer = Label("One Player")
        addCssToLabelMenuOption(onePlayer, scene, pane, StateFactory.createNewOnePlayerGameState())

        val twoPlayer = Label("Two Player")
        addCssToLabelMenuOption(twoPlayer, scene, pane, StateFactory.createNewTwoPlayerGameState())

        val loadGame = Label("Load Game")
        addCssToLabelMenuOption(loadGame, scene, pane, ConfigManager.readState())

        options.children.addAll(onePlayer, twoPlayer, loadGame)
        layout.children.addAll(name, options)
    }

    private fun addCssToLabelMenuOption(
        onePlayer: Label,
        scene: Scene,
        pane: StackPane,
        newGameState: GameState
    ) {
        onePlayer.textFill = Color.WHITE
        onePlayer.style = "-fx-font-family: comic-sans; -fx-font-size: 40"
        onePlayer.setOnMouseEntered {
            onePlayer.textFill = Color.RED
            onePlayer.cursor = Cursor.HAND
        }
        onePlayer.setOnMouseExited {
            onePlayer.textFill = Color.WHITE
        }
        onePlayer.setOnMouseClicked {
            scene.root = pane
            gameState = newGameState
            gameState.addElementsToView(facade.elements)
        }
    }

    private fun addCssToLabel(name: Label) {
        name.textFill = Color.WHITE
        name.style = "-fx-font-family: arial; -fx-font-size: 150"
    }

    private fun addCssToLayout(layout: VBox) {
        layout.alignment = Pos.CENTER
        layout.id = "pane"
    }

    private fun addCssToDivs(div1: HBox, div2: HBox, lives1: Label, lives2: Label): List<HBox> {
        div1.alignment= Pos.TOP_LEFT
        div2.alignment= Pos.TOP_CENTER
        div1.children.addAll(lives1, lives2)
        div1.padding= Insets(10.0,10.0,10.0,10.0)
        div2.padding= Insets(10.0,10.0,10.0,10.0)
        return listOf<HBox>(div1, div2)
    }

    private fun addCssToLives(lives1: Label, lives2: Label): List<Label> {
        lives1.style = "-fx-font-family: VT323; -fx-font-size: 100"
        lives2.style = "-fx-font-family: VT323; -fx-font-size: 100"
        lives1.textFill = Color.color(0.9, 0.9, 0.9)
        lives2.textFill = Color.color(0.9, 0.9, 0.9)
        lives1.id = "lives1"
        lives2.id = "lives2"
        return listOf(lives1, lives2)
    }

    fun pause(){
        facade.stop()
    }

    override fun stop() {
        facade.stop()
        keyTracker.stop()
    }

}