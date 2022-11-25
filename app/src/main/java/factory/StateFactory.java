package factory;

import state.GameState;

import java.util.ArrayList;
import java.util.List;

public class StateFactory {

    public static GameState createTestGameState(){
        return new GameState(800,800, List.of(), List.of(EntityFactory.createDefaultShipControllerForTesting()), new ArrayList<>());
    }

    public static GameState createTestGameStateTwoPlayers(){
        return new GameState(800,800, List.of(), List.of(EntityFactory.createDefaultShipControllerForTesting(), EntityFactory.createDefaultShipControllerForTesting()), new ArrayList<>());
    }
}
