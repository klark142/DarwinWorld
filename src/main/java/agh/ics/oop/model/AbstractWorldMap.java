package agh.ics.oop.model;

import agh.ics.oop.MapVisualizer;
import agh.ics.oop.model.enums.MapDirection;

import java.util.*;
import java.util.Map;

public abstract class AbstractWorldMap implements WorldMap{
    protected java.util.Map<Vector2d, Animal> animals = new HashMap<>();
    protected Map<Vector2d, Plant> grasses = new HashMap<>();
    public List<MapChangeListener> observers = new ArrayList<>();


    @Override
    public Collection<WorldElement> getElements() {
        List<WorldElement> elements = new ArrayList<>();
        elements.addAll(animals.values());
        elements.addAll(grasses.values());
        return elements;
    }
    protected void mapChanged(String message) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, message);
        }
    }
    @Override
    public boolean place(Animal animal){
        if (canMoveTo(animal.getPosition())){
            animals.put(animal.getPosition(), animal);
            mapChanged("Animal placed at position " + animal.getPosition());
            return true;
        }
        return false;   //?
    }

    @Override
    public void move(Animal animal, MapDirection direction) {
        Vector2d oldPosition = animal.getPosition();
        animal.move(direction, this);
        Vector2d newPosition = animal.getPosition();
        if (!oldPosition.equals(newPosition)) {
            animals.remove(oldPosition);
            animals.put(newPosition, animal);
            notifyObservers(animal.toStringNr() + " moved from " + oldPosition + " to " + newPosition);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        if (animals.containsKey(position)) {
            return animals.get(position);
        } else {
            return grasses.get(position);
        }
    }

    private final MapVisualizer visualizer = new MapVisualizer(this);

    @Override
    public String toString() {
        Boundary bounds = getCurrentBounds();
        return visualizer.draw(bounds.lowerLeft(), bounds.upperRight());
    }

    @Override
    public abstract Boundary getCurrentBounds();

    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }
    public void notifyObservers(String message) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged(this, message);
        }
    }
}
