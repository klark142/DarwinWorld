package agh.ics.oop;

import agh.ics.oop.model.enums.BehaviourType;
import agh.ics.oop.model.enums.MapType;

public class Configuration {
    public int width = 0;
    public int height = 0;
    public int energyPerPlant = 0;
    public int startPlantAmount = 0;
    public int startAnimalAmount = 0;
    public int startAnimalEnergy = 0;
    public int minimalReproductionEnergy = 0;
    public int reproductionEnergyCost = 0;
    public int minimalMutationAmount = 0;
    public int maximumMutationAmount = 0;
    public MapType mapType = MapType.EQUATOR_PREFERRED;
    public BehaviourType animalBehaviourType = BehaviourType.NORMAL;
    public int genotypeSize = 10;

    public int getGenotypeSize() {
        return genotypeSize;
    }
}
