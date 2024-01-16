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
    protected Map<Vector2d, Integer> totalPlantsAmount;
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
        this.totalPlantsAmount = new HashMap<>();
    }

    @Override
    public void placePlants(int amount, List<Vector2d> corpses) {
        if (isFull(0, height)) {
            return;
        }
        int freePlace = freePlaces();
        if (amount > freePlace) {
            amount = freePlace;
        }
        int lower = (int) (height * 0.4);
        int upper = (int) (height * 0.6);
        int amount_equator = (int) (0.8 * amount);
        int curr_plant = 0;
        while (curr_plant < amount_equator) {
            while (isFull(lower, upper)) {
                if (lower - 1 >= 0) {
                    lower -= 1;
                }
                if (upper + 1 <= height) {
                    upper += 1;
                }
            }

            int x = random.nextInt(width + 1);
            int y = (random.nextInt(upper - lower + 1)) + lower;
            Vector2d newpos = new Vector2d(x, y);
            if (!isOccupied(newpos)) {
                Plant newPlant = new Plant(newpos);
                plants.put(newPlant.getPosition(), newPlant);
                curr_plant ++;

                // updating total plants amount
                int currentCount = totalPlantsAmount.getOrDefault(newpos, 0);
                totalPlantsAmount.put(newpos, currentCount + 1);
            }
        }
        while (curr_plant < amount) {
            int x = random.nextInt(width + 1);
            int y = random.nextBoolean() ? random.nextInt(lower) :
                    (random.nextInt(height - upper + 1)) + upper;
            Vector2d newpos = new Vector2d(x, y);
            if (!isOccupied(newpos)) {
                Plant newPlant = new Plant(newpos);
                plants.put(newPlant.getPosition(), newPlant);
                curr_plant++;

                // updating total plants amount
                int currentCount = totalPlantsAmount.getOrDefault(newpos, 0);
                totalPlantsAmount.put(newpos, currentCount + 1);
            }
        }
    }

    public boolean isFull(int lower, int upper) {
        for (int j = lower; j <= upper; j++) {
            for (int i = 0; i <= width; i++) {
                Vector2d position = new Vector2d(i, j);
                if (!isOccupied(position)) {
                    return false;
                }
            }
        }
        return true;
    }

    public int freePlaces() {
        int counter = 0;
        for (int i = 0; i <= width; i++) {
            for (int j = 0; j <= height; j++) {
                Vector2d position = new Vector2d(i, j);
                if (!isOccupied(position)) {
                    counter += 1;
                }
            }
        }
        return counter;
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
