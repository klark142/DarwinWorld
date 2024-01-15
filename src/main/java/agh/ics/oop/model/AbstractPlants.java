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
    public static int [][] totalPlantsAmount;
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
        totalPlantsAmount = new int[width + 1][height + 1];
    }

    @Override
    public void placePlants(int amount, List<Vector2d> corpses) {
        if (isFull(0, height)) {
            amount = 0;
        }
        int freePlace = freePlaces();
        if (freePlace == 0){
            amount = 0;
        }
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
                else{
                    amount_equator = 0;
                }
            }

            int x = random.nextInt(width + 1);
            int y = 1;
            if ((upper - lower + 1) + lower > 1){
                y = (random.nextInt(upper - lower + 1)) + lower;
            }

            Vector2d newpos = new Vector2d(x, y);
            if (!isOccupied(newpos)) {
                Plant newPlant = new Plant(newpos);
                plants.put(newPlant.getPosition(), newPlant);
                curr_plant ++;
                totalPlantsAmount[y][x] += 1;
            }
        }
        while (curr_plant < amount) {
            int x = random.nextInt(width + 1);
            int height2 = height - upper + 1;
            if (height2 <1){
                height2 = 1;
            }
            if (lower == 0){
                lower = 1;
            }
            int y = random.nextBoolean() ? random.nextInt(lower) :
                    (random.nextInt(height2)) + upper;

            Vector2d newpos = new Vector2d(x, y);
            if (!isOccupied(newpos)) {
                Plant newPlant = new Plant(newpos);
                plants.put(newPlant.getPosition(), newPlant);
                curr_plant++;
                totalPlantsAmount[y][x] += 1;
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

    public static int[][] getTotalPlantsAmount() {
        return totalPlantsAmount;
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
