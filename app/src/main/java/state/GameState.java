package state;

import controller.ShipController;
import generator.IdGenerator;
import generator.PositionGenerator;
import generator.SizeGenerator;
import generator.VectorGenerator;
import model.Asteroid;
import model.Serializable;
import movement.KeyMovement;
import movement.Mover;
import movement.util.Position;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import parser.AsteroidParser;
import parser.ShipControllerParser;

import java.util.ArrayList;
import java.util.List;

public class GameState implements Serializable {

    private final double width;
    private final double height;
    private final List<Mover> entities;
    private final List<ShipController> ships;

    private final List<String> idsToRemove;

    //TODO add scores map shipId integer

    public GameState(double width, double height, List<Mover> entities, List<ShipController> ships, List<String> idsToRemove) {
        this.width = width;
        this.height = height;
        this.entities = entities;
        this.ships = ships;
        this.idsToRemove = idsToRemove;
    }

    public GameState handleShipAction(String id, KeyMovement movement) {

        ShipController controller = findShipById(id);

        List<ShipController> newControllers = getNewControllersWithoutShip(id);

        List<Mover> newEntities = new ArrayList<>(entities);
        ShipController newController = controller;
        switch (movement){
            case ACCELERATE -> newController = validatePosition(controller.move());
            case TURN_LEFT -> newController = controller.rotate(-15);
            case TURN_RIGHT -> newController = controller.rotate(15);
            case SHOOT -> newEntities.addAll(controller.shoot());
            case STOP -> newController = controller.stop();
        }
        newControllers.add(newController);
        return new GameState(width, height, newEntities, newControllers, idsToRemove);
    }

    private ShipController validatePosition(ShipController move) {
        double x = move.getShipMover().getPosition().getX();
        double y = move.getShipMover().getPosition().getY();

        if (x > width) x = 0;
        if (x < 0) x = width;
        if (y > height) y = 0;
        if (y < 0) y = height;

        return move.updatePosition(new Position(x, y));
    }

    private List<ShipController> getNewControllersWithoutShip(String id) {
        List<ShipController> newControllers = new ArrayList<>(ships);
        return new ArrayList<>(newControllers.stream().filter(entity -> !entity.getId().equals(id)).toList());
    }

    private ShipController findShipById(String id) {
        for (ShipController controller : getShipControllers()) {
            if (controller.getId().equals(id)) return controller;
        }
        return null;
    }

    public GameState moveEntities() {

        List<Mover> newEntities = new ArrayList<>();

        if (Math.random() < 0.05) {
            newEntities.add(new Mover<>(new Asteroid(IdGenerator.generateId(), SizeGenerator.generateSize()), PositionGenerator.generatePosition(width, height), VectorGenerator.generateVector(), 10, new AsteroidParser()));
        }

        List<String> newIdsToRemove = new ArrayList<>();
        moveAndFilterEntities(newEntities, newIdsToRemove);
        return new GameState(width, height, newEntities, ships, newIdsToRemove);

    }

    private void moveAndFilterEntities(List<Mover> newEntities, List<String> newIdsToRemove) {
        for (Mover entity : entities) {
            Mover newEntity = entity.move();
            if (newEntity.getPosition().getX() < width && newEntity.getPosition().getY() < height && newEntity.getPosition().getX() >= 0 && newEntity.getPosition().getY() >= 0) {
                newEntities.add(newEntity);
            }
            else {
                newIdsToRemove.add(newEntity.getId());
            }
        }

    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public List<Mover> getEntities() {
        return entities;
    }

    public List<ShipController> getShips() {
        return ships;
    }

    public List<ShipController> getShipControllers() {
        return ships;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jo = new JSONObject();
        jo.put("width", width);
        jo.put("height", height);
        jo.put("entities", createEntityArray());
        jo.put("ships", createShipArray());
        return jo;
    }

    private JSONArray createShipArray() {
        JSONArray ja = new JSONArray();
        for (ShipController ship : ships) {
            ja.add(ship.toJson());
        }
        return ja;
    }

    private JSONArray createEntityArray() {
        JSONArray ja = new JSONArray();
        for (Mover entity : entities) {
            ja.add(entity.toJson());
        }
        return ja;
    }

    public List<Mover> getEntitiesCopy() {
        return new ArrayList<>(entities);
    }

    public boolean existsEntity(String id){
        for (Mover entity : entities) {
            if (entity.getId().equals(id)) return true;
        }
        return entityIsNotAController(id);
    }

    private boolean entityIsNotAController(String id) {
        for (ShipController controller : ships) {
            if (controller.getId().equals(id)) return true;
        }
        return false;
    }

    public List<String> getIdsToRemove() {
        return idsToRemove;
    }
}
