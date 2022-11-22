package edu.austral.ingsis.starships

import edu.austral.ingsis.starships.ui.*
import factory.EntityFactory
import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.stage.Stage
import movement.util.Position
import movement.util.Vector
import parser.ModelToElementModelParser

fun main() {
    launch(Starships::class.java)
}

class MyStarships() : Application() {
    private val imageResolver = CachedImageResolver(DefaultImageResolver())
    private val facade = ElementsViewFacade(imageResolver)
    private val keyTracker = KeyTracker()

    companion object {
        val STARSHIP_IMAGE_REF = ImageRef("starship", 70.0, 70.0)
    }

    override fun start(primaryStage: Stage) {

//        val starship = ModelToElementModelParser.parse(EntityFactory.createDefaultShipMover(
//            Position(
//                400.0,
//                400.0
//            ), Vector(90.0)))
//        facade.elements[starship.id] = starship

        facade.timeListenable.addEventListener(TimeListener(facade.elements))
        facade.collisionsListenable.addEventListener(CollisionListener())
        //keyTracker.keyPressedListenable.addEventListener(KeyPressedListener(starship))

        val scene = Scene(facade.view)
        keyTracker.scene = scene

        primaryStage.scene = scene
        primaryStage.height = 800.0
        primaryStage.width = 800.0

        facade.start()
        keyTracker.start()
        primaryStage.show()
    }

    override fun stop() {
        facade.stop()
        keyTracker.stop()
    }
}

class MyTimeListener(private val elements: Map<String, ElementModel>) : EventListener<TimePassed> {
    override fun handle(event: TimePassed) {
        //
    }
}

class MyCollisionListener() : EventListener<Collision> {
    override fun handle(event: Collision) {
        println("${event.element1Id} ${event.element2Id}")
    }

}

class MyKeyPressedListener(private val starship: ElementModel): EventListener<KeyPressed> {
    override fun handle(event: KeyPressed) {
        when(event.key) {
            KeyCode.UP -> starship.y.set(starship.y.value - 5 )
            KeyCode.DOWN -> starship.y.set(starship.y.value + 5 )
            KeyCode.LEFT -> starship.x.set(starship.x.value - 5 )
            KeyCode.RIGHT -> starship.x.set(starship.x.value + 5 )
            else -> {}
        }
    }

}