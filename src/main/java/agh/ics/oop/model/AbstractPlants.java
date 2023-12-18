package agh.ics.oop.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class AbstractPlants implements IPlants {
    protected Map<Vector2d, Plant> plants = new HashMap<>();
    protected Random random = new Random();
    protected int width;
    protected int height;
    private WorldMap worldMap;
    public Map<Vector2d, Plant> getPlants() {
        return plants;
    }
    public WorldMap getWorldMap() {
        return worldMap;
    }

    public AbstractPlants(WorldMap worldMap) {
        this.worldMap = worldMap;
        this.height = getWorldMap().getHeight();
        this.width = getWorldMap().getWidth();
    }

    @Override
    public void placePlants(int amount, List<Vector2d> corpses) {
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
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public Plant objectAt(Vector2d position) {
        for (Vector2d currPos : getPlants().keySet()) {
            if (currPos.equals(position)) {
                return getPlants().get(position);
            }
        }
        return null;
    }
}
