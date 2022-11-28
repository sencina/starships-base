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
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.geometry.Pos
import javafx.scene.Cursor
import parser.ModelToUIParser
import java.util.Collections

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
        var livesDiv = addCssToDivs(HBox(50.0), livesList[0], livesList[1])
        var pointsList = addCssToPoints(Label("0"), Label("0"))
        var pointsDiv = addCssToPointsList(HBox(50.0), pointsList[0], pointsList[1])
        lives.children.addAll(livesDiv, pointsDiv)

        val pane=StackPane()
        val layout = VBox(100.0)

        val scene = setGameScene(pane, lives, layout, primaryStage)

        initializeMenu(layout, scene, pane)

        addListeners(livesList[0], livesList[1], pointsList[0], pointsList[1])

        startGame(primaryStage)
    }

    private fun addCssToPointsList(hBox: HBox, label: Label, label1: Label): HBox {
        hBox.alignment= Pos.TOP_RIGHT
        hBox.children.addAll(label, label1)
        hBox.padding= Insets(10.0,10.0,10.0,10.0)
        return hBox
    }

    private fun addCssToPoints(label: Label, label1: Label): List<Label> {
        label.style = "-fx-font-family: VT323; -fx-font-size: 100"
        label1.style = "-fx-font-family: VT323; -fx-font-size: 100"
        label.textFill = Color.BLACK
        label1.textFill = Color.BLACK
        label.id = "points1"
        label1.id = "points2"
        return listOf(label, label1)
    }

    private fun addListeners(label: Label, label1: Label, score1: Label, score2: Label) {
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

                updateLives(label, label1)

                updateScore(score1,score2)

                validateVictory(label, label1)

                gameState = GameState(
                    gameState.width, gameState.height, newEntities, newShips, newIdsToRemove, gameState.points,
                    gameState.isPaused
                )
            }

            private fun updateScore(score1: Label, score2: Label) {
                score1.text = assertExistence(gameState.points["STARSHIP-0"].toString())
                score2.text = assertExistence(gameState.points["STARSHIP-1"].toString())
            }

            private fun updateLives(label: Label, label1: Label) {
                label.text = assertExistence(gameState.findShipById("STARSHIP-0")?.lives.toString())
                label1.text = assertExistence(gameState.findShipById("STARSHIP-1")?.lives.toString())
            }

            private fun assertExistence(toString: String): String {
                return if (toString == "null") "" else toString
            }

            private fun validateVictory(label: Label, label1: Label) {
                if (gameState.ships.size == 1 && startingShips>1 && !gameState.isPaused) {
                    pause(gameState.ships[0].id + " won!", "",label, label1)
                    facade.elements.clear()
                }
                if (gameState.ships.size == 0 && !gameState.isPaused) {
                    pause("LOST","", label, label1)
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
                pause("R: Resume","G: Save", label, label1)
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

        val twoPlayer = Label("Two Players")
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
            newGameState.addElementsToView(facade.elements)
            startingShips = newGameState.ships.size
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

    private fun addCssToDivs(div1: HBox, lives1: Label, lives2: Label): HBox {
        div1.alignment= Pos.TOP_LEFT
        div1.children.addAll(lives1, lives2)
        div1.padding= Insets(10.0,10.0,10.0,10.0)
        return div1
    }

    private fun addCssToLives(lives1: Label, lives2: Label): List<Label> {
        lives1.style = "-fx-font-family: VT323; -fx-font-size: 100"
        lives2.style = "-fx-font-family: VT323; -fx-font-size: 100"
        lives1.textFill = Color.BLACK
        lives2.textFill = Color.BLACK
        lives1.id = "lives1"
        lives2.id = "lives2"
        return listOf(lives1, lives2)
    }

    fun pause(text: String, text1: String, label: Label, label1: Label) {
        facade.stop()
        label.text = text
        label1.text = text1
    }

    override fun stop() {
        facade.stop()
        keyTracker.stop()
    }

}