package agh.ics.oop.model;

//import agh.ics.oop.PositionAlreadyOccupiedException;
import agh.ics.oop.model.enums.MapDirection;

import java.util.Collection;
import java.util.List;
import java.util.UUID;  //to jest do getID()

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo, idzik
 */
public interface WorldMap extends MoveValidator {

    /**
     * Place a animal on the map.
     *
     * @param animal The animal to place on the map.
     * @return True if the animal was placed. The animal cannot be placed if the move is not valid.
     */
    boolean place(Animal animal);
//    boolean place(Animal animal);
    /**
     * Moves an animal (if it is present on the map) according to specified direction.
     * If the move is not possible, this method has no effect.
     */
    void move(Animal animal, MapDirection direction);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an animal at a given position.
     *
     * @param position The position of the animal.
     * @return animal or null if the position is not occupied.
     */
    WorldElement objectAt(Vector2d position);

    Collection<WorldElement> getElements();
    Boundary getCurrentBounds();
    void addObserver(MapChangeListener observer);
    UUID getId();
//    public void GrassOnMap();
    public void GrassOnMapExtra(int amount, List<Vector2d> corpses);

}