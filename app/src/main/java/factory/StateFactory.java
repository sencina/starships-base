package factory;

import movement.Position;
import state.GameState;

import java.util.ArrayList;
import java.util.List;
import static config.Constants.*;

public class StateFactory {

    public static GameState createTestGameState(){
        return new GameState(GAME_WIDTH,GAME_HEIGHT, List.of(), List.of(EntityFactory.createDefaultShipControllerForTesting()), new ArrayList<>());
    }

    public static GameState createTestGameStateTwoPlayers(){
        return new GameState(GAME_WIDTH,GAME_HEIGHT, List.of(), List.of(EntityFactory.createDefaultShipControllerForTesting(), EntityFactory.createDefaultShipControllerForTesting()), new ArrayList<>());
    }

    public static GameState createNewOnePlayerGameState(){
        return new GameState(GAME_WIDTH,GAME_HEIGHT, List.of(EntityFactory.createAsteroidMover(new Position(400,400),0)), List.of(EntityFactory.createP1DefaultShipController()), new ArrayList<>());
    }

    public static GameState createNewTwoPlayerGameState(){
        return new GameState(GAME_WIDTH,GAME_HEIGHT, List.of(EntityFactory.createAsteroidMover(new Position(400,400),0)), List.of(EntityFactory.createP1DefaultShipController(), EntityFactory.createP2DefaultShipController()), new ArrayList<>());
    }
}
