package agh.ics.oop.model;

import java.util.*;
import java.util.Map;

public class EquatorPrefered extends AbstractWorldMap {
    protected Map<Vector2d, Plant> plants = new HashMap<>();
    protected Random random = new Random();
    protected final int plantStart;
    protected int width;
    protected int height;
    protected final UUID id = UUID.randomUUID();

    public EquatorPrefered(int plantStart, int width, int height) {
        this.plantStart = plantStart;
        this.width = width;
        this.height = height;
    }

    public void GrassOnMap() {
        int lower = (int)(height*0.5);
        int upper = (int)(height*0.6);
        int amount_equator = (int)(0.8*plantStart);
        while (grasses.size() < amount_equator) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            Vector2d newpos = new Vector2d(x, y);
            if (!isOccupied(newpos) & y>=lower & y<= upper) {
                Plant newGrass = new Plant(newpos);
                plants.put(newGrass.getPosition(), newGrass);
            }
        }
        while (grasses.size() < plantStart) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            Vector2d newpos = new Vector2d(x, y);
            if (!isOccupied(newpos) & (y < lower | y > upper)) {
                Plant newGrass = new Plant(newpos);
                plants.put(newGrass.getPosition(), newGrass);
            }
        }
    }

    public void GrassOnMapExtra(int amount, List<Vector2d> corpses) {
        int lower = (int)(height*0.5);
        int upper = (int)(height*0.6);
        int amount_equator = (int)(0.8*amount);
        int curr_plant = 0;
        while (curr_plant < amount_equator) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            Vector2d newpos = new Vector2d(x, y);
            if (!isOccupied(newpos) & y>=lower & y<= upper) {
                Plant newPlant = new Plant(newpos);
                plants.put(newPlant.getPosition(), newPlant);
                curr_plant ++;
            }
        }
        while (curr_plant < amount) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            Vector2d newpos = new Vector2d(x, y);
            if (!isOccupied(newpos) & (y < lower | y > upper)) {
                Plant newPlant = new Plant(newpos);
                plants.put(newPlant.getPosition(), newPlant);
                curr_plant ++;
            }
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(new Vector2d(0, 0)) && position.precedes(new Vector2d(width - 1, height - 1)) && !isOccupied(position);
    }

    @Override
    public Boundary getCurrentBounds() {
        Vector2d lowerLeft = new Vector2d(0, 0);
        Vector2d upperRight = new Vector2d(width - 1, height - 1);
        return new Boundary(lowerLeft, upperRight);
    }

    @Override
    public UUID getId() {
        return id;
    }


    @Override
    public WorldElement objectAt(Vector2d position) {
        if (animals.containsKey(position)) {
            return animals.get(position);
        } else {
            return plants.get(position);
        }
    }

}
