package agh.ics.oop.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LifegivingCorpse extends AbstractPlants {
    public LifegivingCorpse(WorldMap worldMap) {
        super(worldMap);
    }
    @Override
    public void placePlants(int amount, List<Vector2d> corpses) {
        int currPlant = 0;
        while (currPlant < amount) {
            Vector2d newpos = choosePosition(corpses,3);
            if (!isOccupied(newpos)) {
                Plant newGrass = new Plant(newpos);
                plants.put(newGrass.getPosition(), newGrass);
                currPlant++;
            }
        }
    }
    private Vector2d choosePosition(List<Vector2d> preferredPositions, double preferredFactor) {
        // Check if there are preferred positions
        if (!preferredPositions.isEmpty()) {
            // Calculate the total probability weight for preferred positions
            double totalWeight = preferredPositions.size() * preferredFactor;

            // Check if we should choose a preferred position
            if (random.nextDouble() < totalWeight / (totalWeight + (width * height - preferredPositions.size()))) {
                return preferredPositions.get(random.nextInt(preferredPositions.size()));
            }
        }
        // Choose a random position
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        return new Vector2d(x, y);
    }
}