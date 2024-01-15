package agh.ics.oop;

import agh.ics.oop.Configuration;

public class ConfigurationValidator {
    public static void validate(Configuration config) throws IllegalArgumentException {
        if (config.getWidth() <= 0 || config.getHeight() <= 0) {
            throw new IllegalArgumentException("Width and height must be positive integers.");
        }

        if (config.getEnergyPerPlant() <= 0 ||
                config.getStartPlantAmount() < 0 ||
                config.getPlantsPerDay() < 0 ||
                config.getStartAnimalAmount() < 0 ||
                config.getStartAnimalEnergy() <= 0 ||
                config.getMinimalReproductionEnergy() < 0 ||
                config.getReproductionEnergyCost() <= 0 ||
                config.getMinimalMutationAmount() < 0 ||
                config.getMaximumMutationAmount() < 0 ||
                config.getGenotypeSize() <= 0 ||
                config.getRefreshRate() <= 0) {
            throw new IllegalArgumentException("All numerical values must be positive integers.");
        }
    }
}
