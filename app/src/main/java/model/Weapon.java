package model;

import movement.Mover;
import movement.Position;

import java.util.List;

public interface Weapon extends Serializable{
     List<Mover<Bullet>> shoot(Position position, double angle, String ownerId);
}
