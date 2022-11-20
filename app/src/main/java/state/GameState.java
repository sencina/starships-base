package state;

import controller.ShipController;
import generator.IdGenerator;
import generator.PositionGenerator;
import generator.SizeGenerator;
import generator.VectorGenerator;
import model.Asteroid;
import movement.KeyMovement;
import movement.Mover;

import java.util.List;

public class GameState {

    private final double width;
    private final double height;
    private final List<Mover> entities;

    public GameState(double width, double height, List<Mover> entities) {
        this.width = width;
        this.height = height;
        this.entities = entities;
    }

    public GameState moveShip(String id, KeyMovement movement, double time) {

        Mover mover = entities.stream()
                .filter(entity -> entity.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No ship with id " + id));

        List<Mover> newEntities = new java.util.ArrayList<>(entities.stream().filter(entity -> !entity.getEntity().getId().equals(id)).toList());

        Mover<ShipController> controllerMover = (Mover<ShipController>) mover;
        switch (movement){
            case ACCELERATE -> newEntities.add(mover.move(time));
            case TURN_LEFT -> newEntities.add(mover.rotate(-15));
            case TURN_RIGHT -> newEntities.add(mover.rotate(15));
            case SHOOT -> newEntities.add(controllerMover.getEntity().shoot(mover.getPosition(), mover.getVector()));
            case STOP -> newEntities.add(mover.stop());
        }

        return new GameState(width, height, newEntities);

    }

    public GameState moveEntities(double time) {

        if (Math.random() < 0.05) {
            List<Mover> newEntities = new java.util.ArrayList<>(entities);
            newEntities.add(new Mover<>(new Asteroid(IdGenerator.generateId(), SizeGenerator.generateSize()), PositionGenerator.generatePosition(width, height), VectorGenerator.generateVector(), 10));
            return new GameState(width, height, newEntities.stream().map(entity -> entity.move(time)).toList());
        }else {
            List<Mover> newEntities = entities.stream()
                    .map(entity -> entity.move(time))
                    .toList();

            return new GameState(width, height, newEntities);
        }
    }

    public List<Mover> getEntities() {
        return entities;
    }
}
