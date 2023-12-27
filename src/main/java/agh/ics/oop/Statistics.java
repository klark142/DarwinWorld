package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.WorldMap;
import com.sun.source.tree.Tree;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Statistics {
    private int totalAnimals;
    private int totalPlants;
    private int freeFields;
    private Map<String, Integer> genotypeCounts;
    private double averageEnergy;
    private double averageLifespan;
    private double averageChildren;
    private WorldMap worldMap;

    // TODO tracking chosen animal logic
    private Animal chosenAnimal;
    private boolean trackingChosenAnimal;

    // getters
    public int getTotalAnimals() {
        return totalAnimals;
    }

    public int getTotalPlants() {
        return totalPlants;
    }

    public int getFreeFields() {
        return freeFields;
    }

    public Map<String, Integer> getGenotypeCounts() {
        return genotypeCounts;
    }

    public double getAverageEnergy() {
        return averageEnergy;
    }

    public double getAverageLifespan() {
        return averageLifespan;
    }

    public double getAverageChildren() {
        return averageChildren;
    }

    public Animal getChosenAnimal() {
        return chosenAnimal;
    }

    // setters


    public void setTotalAnimals(int totalAnimals) {
        this.totalAnimals = totalAnimals;
    }

    public void setTotalPlants(int totalPlants) {
        this.totalPlants = totalPlants;
    }

    public void setFreeFields(int freeFields) {
        this.freeFields = freeFields;
    }

    public void setGenotypeCounts(Map<String, Integer> genotypeCounts) {
        this.genotypeCounts = genotypeCounts;
    }

    public void setAverageEnergy(double averageEnergy) {
        this.averageEnergy = averageEnergy;
    }

    public void setAverageLifespan(double averageLifespan) {
        this.averageLifespan = averageLifespan;
    }

    public void setAverageChildren(double averageChildren) {
        this.averageChildren = averageChildren;
    }

    public void setChosenAnimal(Animal chosenAnimal) {
        this.chosenAnimal = chosenAnimal;
    }

    public void setTrackingChosenAnimal(boolean trackingChosenAnimal) {
        this.trackingChosenAnimal = trackingChosenAnimal;
    }

    public boolean isTrackingChosenAnimal() {
        return trackingChosenAnimal;
    }

    // constructors

    public Statistics(WorldMap worldMap) {
        this.genotypeCounts = new HashMap<>();
        this.worldMap = worldMap;
    }

    // proper class functions

    public void recordAnimalBirth() {
        totalAnimals++;
        updateAverageChildren();
    }


    public void recordAnimalDeath() {
        totalAnimals--;
        updateAverageLifespan();
    }

    public void recordPlantSpawn() {
        totalPlants++;
    }

    public void recordPlantConsumption() {
        totalPlants--;
    }

    public void updateFreeFields(int freeFields) {
        this.freeFields = freeFields;
    }

    public void startTrackingAnimal(Animal animal) {
        chosenAnimal = animal;
        trackingChosenAnimal = true;
    }

    public void stopTrackingAnimal() {
        trackingChosenAnimal = false;
    }

    public void updateAverageEnergy() {
        int energySum = 0;
        for (TreeSet<Animal> treeSet : worldMap.getAnimals().values()) {
            for (Animal animal : treeSet) {
                energySum += animal.getEnergyPoints();
            }
        }

        setAverageEnergy((double) energySum / totalAnimals);
    }

    public void updateAverageLifespan() {
        int lifespanSum = 0;
        for (Animal animal : worldMap.getDeadAnimals()) {
            lifespanSum += animal.getAge();
        }

        setAverageLifespan((double) lifespanSum / worldMap.getDeadAnimals().size());
    }

    public void updateAverageChildren() {
        int childrenSum = 0;
        for (TreeSet<Animal> treeSet : worldMap.getAnimals().values()) {
            for (Animal animal : treeSet) {
                childrenSum += animal.getChildrenNumber();
            }
        }

        setAverageChildren((double) childrenSum / totalAnimals);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Total Animals: ").append(totalAnimals).append("\n");
        sb.append("Total Plants: ").append(totalPlants).append("\n");
        sb.append("Free Fields: ").append(freeFields).append("\n");
        sb.append("Genotype Counts: ").append(genotypeCounts).append("\n");
        sb.append("Average Energy: ").append(averageEnergy).append("\n");
        sb.append("Average Lifespan: ").append(averageLifespan).append("\n");
        sb.append("Average Children: ").append(averageChildren).append("\n");

        // TODO: Add chosen animal tracking information if applicable
        if (trackingChosenAnimal && chosenAnimal != null) {
            sb.append("\nChosen Animal Tracking:\n");
            sb.append("Chosen Animal: ").append(chosenAnimal).append("\n");
            // Add more chosen animal tracking information as needed
        }

        return sb.toString();
    }

}
