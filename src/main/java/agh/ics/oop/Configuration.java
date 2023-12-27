package agh.ics.oop;

import agh.ics.oop.model.enums.BehaviourType;
import agh.ics.oop.model.enums.MapType;

public class Configuration {
    public int width = 10;
    public int height = 10;
    public int energyPerPlant = 3;
    public int startPlantAmount = 5;
    public int plantsPerDay = 2;
    public int startAnimalAmount = 5;
    public int startAnimalEnergy = 10;
    public int minimalReproductionEnergy = 2;
    public int reproductionEnergyCost = 3;
    public int minimalMutationAmount = 0;
    public int maximumMutationAmount = 0;
    public MapType mapType = MapType.EQUATOR_PREFERRED;
    public BehaviourType animalBehaviourType = BehaviourType.NORMAL;
    public int genotypeSize = 10;

    public int getPlantsPerDay() {
        return plantsPerDay;
    }

    public int getGenotypeSize() {
        return genotypeSize;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getEnergyPerPlant() {
        return energyPerPlant;
    }

    public int getStartPlantAmount() {
        return startPlantAmount;
    }

    public int getStartAnimalAmount() {
        return startAnimalAmount;
    }

    public int getStartAnimalEnergy() {
        return startAnimalEnergy;
    }

    public int getMinimalReproductionEnergy() {
        return minimalReproductionEnergy;
    }

    public int getReproductionEnergyCost() {
        return reproductionEnergyCost;
    }

    public int getMinimalMutationAmount() {
        return minimalMutationAmount;
    }

    public int getMaximumMutationAmount() {
        return maximumMutationAmount;
    }

    public MapType getMapType() {
        return mapType;
    }

    public BehaviourType getAnimalBehaviourType() {
        return animalBehaviourType;
    }
}
