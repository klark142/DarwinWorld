package agh.ics.oop.model;

import java.util.*;
import java.util.Map;

public class EquatorPreferred extends AbstractPlants {
    protected final UUID id = UUID.randomUUID();

    public EquatorPreferred(WorldMap worldMap) {
        super(worldMap);
    }

//    @Override
//    public boolean canMoveTo(Vector2d position) {
//        return position.follows(new Vector2d(0, 0)) && position.precedes(new Vector2d(width - 1, height - 1)) && !isOccupied(position);
//    }
//
//    @Override
//    public Boundary getCurrentBounds() {
//        Vector2d lowerLeft = new Vector2d(0, 0);
//        Vector2d upperRight = new Vector2d(width - 1, height - 1);
//        return new Boundary(lowerLeft, upperRight);
//    }
//
//    @Override
//    public UUID getId() {
//        return id;
//    }
//
//

}