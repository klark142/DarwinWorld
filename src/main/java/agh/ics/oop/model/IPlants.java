package agh.ics.oop.model;

import java.util.List;
import java.util.Map;

public interface IPlants {
    public void placePlants(int amount, List<Vector2d> corpses);
    public boolean isOccupied(Vector2d position);
    public Plant objectAt(Vector2d position);

    Map<Vector2d, Plant> getPlants();
}