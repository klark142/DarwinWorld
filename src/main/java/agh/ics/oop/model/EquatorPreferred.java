package agh.ics.oop.model;

import java.util.*;
import java.util.Map;

public class EquatorPreferred extends AbstractPlants {
    protected final UUID id = UUID.randomUUID();

    public EquatorPreferred(WorldMap worldMap) {
        super(worldMap);
    }

    public Map<Vector2d, Integer> getTotalPlantsAmount() {
        return this.totalPlantsAmount;
    }
}
