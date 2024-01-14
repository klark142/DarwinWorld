package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Genotype;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;
import com.sun.source.tree.Tree;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Statistics {
    private int totalAnimals;
    private int totalPlants;
    private int freeFields;
    private Map<Genotype, Integer> genotypeCounts;
    private Genotype mostPopularGenotype;
    private double averageEnergy;
    private double averageLifespan;
    private double averageChildren;
    private WorldMap worldMap;

    // TODO tracking chosen animal logic
    private Animal chosenAnimal;
    private boolean trackingChosenAnimal;

    // writing stats to CSV file
    private ArrayList<String> fileStats = new ArrayList<>();

    // getters

    public ArrayList<String> getFileStats() {
        return fileStats;
    }

    public int getTotalAnimals() {
        return totalAnimals;
    }


    public int getTotalPlants() {
        return totalPlants;
    }

    public int getFreeFields() {
        return freeFields;
    }

    public Map<Genotype, Integer> getGenotypeCounts() {
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

    public WorldMap getWorldMap() {
        return worldMap;
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

    public void addGenotypeToStats(Genotype genotype) {
        // check if genotype is already present in the dict
        Map<Genotype, Integer> map = this.getGenotypeCounts();
        if (map.containsKey(genotype)) {
            int count = map.get(genotype);
            map.put(genotype, count + 1);
        } else {
            map.put(genotype, 1);
        }
    }

    public void updatePopularGenotypes() {
        WorldMap worldmap = this.getWorldMap();
        Set<Animal> animalsInDict = new HashSet<>();

        // get current animals added to dict as a set
        for (Genotype genotype : this.getGenotypeCounts().keySet()) {
            animalsInDict.add(genotype.getAnimal());
        }

        // check if animal was added
        // if not then update stats
        for (Vector2d position : worldmap.getAnimals().keySet()) {
            for (Animal animal : worldmap.getAnimals().get(position)) {
                if (!animalsInDict.contains(animal)) {
                    this.addGenotypeToStats(animal.getGenotype());
                }
            }
        }

        // find the most popular genotype
        int maxCount = Integer.MIN_VALUE;
        Genotype maxGenotype = null;
        for (Map.Entry<Genotype, Integer> entry : this.getGenotypeCounts().entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxGenotype = entry.getKey();
            }
        }
        this.mostPopularGenotype = maxGenotype;
    }

    // for visualising animals that have the most popular genotypes
    public List<Animal> getMostPopularGenotypeAnimals() {
        WorldMap worldmap = this.getWorldMap();
        List<Animal> mostPopularGenotypesAnimals = new ArrayList<>();

        for (Vector2d position : worldmap.getAnimals().keySet()) {
            for (Animal animal : worldmap.getAnimals().get(position)) {
                if (animal.getGenotype() == this.mostPopularGenotype) {
                    mostPopularGenotypesAnimals.add(animal);
                }
            }
        }
        return mostPopularGenotypesAnimals;
    }

    public void removeDeadAnimalFromGenotypes(Animal animal) {
        int count = this.getGenotypeCounts().get(animal.getGenotype());

        if (count != 0) {
            if (count > 1) {
                this.getGenotypeCounts().put(animal.getGenotype(), count - 1);
            } else {
                this.getGenotypeCounts().remove(animal.getGenotype());
            }
        }
    }

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
        chosenAnimal = null;
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
        sb.append("Liczba wszystkich zwierząt: ").append(totalAnimals).append("\n");
        sb.append("Liczba wszystkich roślin: ").append(totalPlants).append("\n");
        sb.append("Wolne pola: ").append(freeFields).append("\n");
        sb.append("Najpopularniejszy genotyp: ").append(mostPopularGenotype).append(
                "\n");
        sb.append("Średnia energia: ").append(averageEnergy).append("\n");
        sb.append("Średnia długość życia: ").append(averageLifespan).append("\n");
        sb.append("Średnia liczba dzieci: ").append(averageChildren).append("\n");

        return sb.toString();
    }

    public String animalTrackingToString() {
        StringBuilder sb = new StringBuilder();
        if (trackingChosenAnimal && chosenAnimal != null) {
            sb.append("\nŚledzenie wybranego zwierzęcia:\n");
            sb.append("Genotyp: ").append(chosenAnimal.getGenotype().toString()).append(
                    "\n");
            sb.append("Aktywny gen: ").append(chosenAnimal.getActivatedGene().toString()).append(
                    "\n");
            sb.append("Energia: ").append(chosenAnimal.getEnergyPoints()).append("\n");
            sb.append("Zjedzone rośliny: ").append(chosenAnimal.getEatenPlantsNumber()).append("\n");
            sb.append("Liczba dzieci: ").append(chosenAnimal.getChildrenNumber()).append(
                    "\n");
            sb.append("Liczba potomków: ").append(chosenAnimal.getDescendantsNumber()).append("\n");
            sb.append("Długość życia: ").append(chosenAnimal.getAge()).append("\n");
            if (chosenAnimal.getDayOfDeath() != 0) {
                sb.append("Dzień śmierci: ").append(chosenAnimal.getDayOfDeath()).append(
                        "\n");
            }
        }
        return sb.toString();
    }

    // handle writing stats to CSV file
    public void addNewData (String newData) {
        getFileStats().add(newData);
    }

    public String generateCSVData() {
        return String.valueOf(getFileStats());
    }

    public void writeCSVToFile(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(generateCSVData());
            System.out.println("CSV file created successfully.");
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }

}
