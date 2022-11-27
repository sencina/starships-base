package state;

import controller.ShipController;
import enums.EntityType;
import model.Collideable;
import model.Serializable;
import movement.KeyMovement;
import movement.Mover;
import movement.Position;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

import static config.Constants.ROTATION_DEGREES;

public class GameState implements Serializable {

    private final double width;
    private final double height;
    private final List<Mover> entities;
    private final List<ShipController> ships;
    private final List<String> idsToRemove;
    private final Map<String, Integer> points;
    private final boolean paused;

    public GameState(double width, double height, List<Mover> entities, List<ShipController> ships, List<String> idsToRemove, Map<String, Integer> points, boolean paused) {
        this.width = width;
        this.height = height;
        this.entities = entities;
        this.ships = ships;
        this.idsToRemove = idsToRemove;
        this.points = points;
        this.paused = paused;
    }

    public GameState(double width, double height, List<Mover> entities, List<ShipController> ships, List<String> idsToRemove, Map<String, Integer> points) {
        this(width, height, entities, ships, idsToRemove, points, false);
    }

    public GameState(double width, double height, List<Mover> entities, List<ShipController> ships, List<String> idsToRemove) {
        this(width, height, entities, ships, idsToRemove, createHashMap(ships), false);
    }

    private static Map<String, Integer> createHashMap(List<ShipController> ships) {
        Map<String, Integer> points = new HashMap<>();
        for (ShipController ship : ships) {
            points.put(ship.getId(), 0);
        }
        return points;
    }

    public GameState handleShipAction(String id, KeyMovement movement) {

        if (paused) return this;

        ShipController controller = findShipById(id);

        List<ShipController> newControllers = getNewControllersWithoutShip(id);

        List<Mover> newEntities = new ArrayList<>(entities);
        ShipController newController = controller;
        switch (movement){
            case ACCELERATE -> newController = validatePosition(controller.accelerate());
            case TURN_LEFT -> newController = controller.rotate(-ROTATION_DEGREES);
            case TURN_RIGHT -> newController = controller.rotate(ROTATION_DEGREES);
            case SHOOT -> newEntities.addAll(controller.shoot());
            case STOP -> newController = controller.stop();
        }
        newControllers.add(newController);
        return new GameState(width, height, newEntities, newControllers, idsToRemove, points, false);
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

    public GameState collideEntities(String id1, String id2){
        List<Mover> newEntities = toMutable(new ArrayList<>(entities).stream().filter(entity -> !entity.getId().equals(id1) && !entity.getId().equals(id2)).toList());
        List<ShipController> newControllers = toMutable(new ArrayList<>(ships).stream().filter(entity -> !entity.getId().equals(id1) && !entity.getId().equals(id2)).toList());
        List<String> newIdsToRemove = new ArrayList<>(idsToRemove);

        Mover entity1 = findEntityById(id1);
        Mover entity2 = findEntityById(id2);

        Map<String, Integer> newMap = addPoints(entity1.getEntity(), entity2.getEntity());

        Optional<Collideable> newEntity1 = entity1.getEntity().collide(entity2.getEntity());
        Optional<Collideable> newEntity2 = entity2.getEntity().collide(entity1.getEntity());

        addNewEntity(newEntities, newControllers, newIdsToRemove, entity1, newEntity1);
        addNewEntity(newEntities, newControllers, newIdsToRemove, entity2, newEntity2);

        return new GameState(width, height, newEntities, newControllers, newIdsToRemove, newMap, paused);
    }

    private Map<String,Integer> addPoints(Collideable points1, Collideable points2){
        Map<String,Integer> newMap = new HashMap<>(points);
        if(!points1.getEntityType().equals(points2.getEntityType())){
            addPointsToMap(newMap, points1);
            addPointsToMap(newMap, points2);
        }
        return newMap;
    }

    private void addPointsToMap(Map<String, Integer> newMap, Collideable points1) {
        if(newMap.containsKey(points1.getId())){
            newMap.put(points1.getId(), newMap.get(points1.getId()) + points1.getPoints().points());
        }
    }

    private <T> List<T> toMutable(List<T> toList) {
        return new ArrayList<>(toList);
    }

    private void addNewEntity(List<Mover> newEntities, List<ShipController> newControllers, List<String> newIdsToRemove, Mover entity1, Optional<Collideable> newEntity1) {
        if (newEntity1.isPresent()) {
            if (entity1.getEntityType().equals(EntityType.STARSHIP)) {
                ShipController controller = findShipById(entity1.getId());
                newControllers.add(controller.updateMover(newEntity1.get()));
            }
            else {
                newEntities.add(new Mover(newEntity1.get(), entity1.getPosition(), entity1.getRotationInDegrees(), entity1.getSpeed(), entity1.getParser()));
            }
        }
        else {
            newIdsToRemove.add(entity1.getId());
        }
    }

    private Mover findEntityById(String id1) {
        for (Mover entity : entities) {
            if (entity.getId().equals(id1)) return entity;
        }
        for (ShipController controller : ships) {
            if (controller.getId().equals(id1)) return controller.getShipMover();
        }
        return null;
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
        jo.put("idsToRemove", createIdsToRemoveArray());
        jo.put("points", points);
        return jo;
    }

    private JSONArray createIdsToRemoveArray() {
        JSONArray ja = new JSONArray();
        for (String id : idsToRemove) {
            ja.add(id);
        }
        return ja;
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

    public List<String> getIdsToRemove() {
        return idsToRemove;
    }

    public GameState changeState(){
        return new GameState(width, height, entities, ships, idsToRemove, points, !paused);
    }
}
