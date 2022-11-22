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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class GameState implements Serializable {

    private final double width;
    private final double height;
    private final List<Mover> entities;
    private final List<ShipController> ships;

    public GameState(double width, double height, List<Mover> entities, List<ShipController> ships) {
        this.width = width;
        this.height = height;
        this.entities = entities;
        this.ships = ships;
    }

    public GameState moveShip(String id, KeyMovement movement, double time) {

        ShipController controller = ships.stream()
                .filter(entity -> entity.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No ship with id " + id));

        List<ShipController> newControllers = new java.util.ArrayList<>(ships.stream().filter(entity -> !entity.getId().equals(id)).toList());
        List<Mover> newEntities = new java.util.ArrayList<>(entities);
        switch (movement){
            case ACCELERATE -> newControllers.add(controller.move(time));
            case TURN_LEFT -> newControllers.add(controller.rotate(-15));
            case TURN_RIGHT -> newControllers.add(controller.rotate(15));
            case SHOOT -> newEntities.addAll(controller.shoot());
            case STOP -> newControllers.add(controller.stop());
        }

        return new GameState(width, height, newEntities, newControllers);

    }

    public GameState moveEntities(double time) {

        if (Math.random() < 0.05) {
            List<Mover> newEntities = new java.util.ArrayList<>(entities);
            newEntities.add(new Mover<>(new Asteroid(IdGenerator.generateId(), SizeGenerator.generateSize()), PositionGenerator.generatePosition(width, height), VectorGenerator.generateVector(), 10));
            return new GameState(width, height, newEntities.stream().map(entity -> entity.move(time)).filter(entity -> entity.getPosition().getX() < width && entity.getPosition().getY() < height && entity.getPosition().getX() >= 0 && entity.getPosition().getY() >= 0).toList(), ships);
        }else {
            List<Mover> newEntities = entities.stream()
                    .map(entity -> entity.move(time)).filter(entity -> entity.getPosition().getX() < width && entity.getPosition().getY() < height && entity.getPosition().getX() >= 0 && entity.getPosition().getY() >= 0)
                    .toList();

            return new GameState(width, height, newEntities,ships);
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
}
