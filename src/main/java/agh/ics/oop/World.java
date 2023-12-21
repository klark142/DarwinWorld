package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;
import java.util.TreeSet;

public class World {
    public static void main(String[] args) {
        // basic settings
        int GENOTYPE_SIZE = 10;
        Genotype.setGenotypeSize(GENOTYPE_SIZE);
        Genotype genotype_first = new Genotype();
        System.out.println(genotype_first);
        Genotype genotype_second = new Genotype();
        System.out.println(genotype_second);

        Genotype genotype_child = genotype_first.crossGenotypes(genotype_second,
                10, 20);
        System.out.println(genotype_child);

        Configuration configuration = new Configuration();

        // place animals on the map
        WorldMap worldMap = new WorldMap(configuration);
        System.out.println(worldMap);
        ConsoleMapDisplay consoleMapDisplay = new ConsoleMapDisplay();
        worldMap.registerMapChangeListener(consoleMapDisplay);
        worldMap.placeRandomAnimals(5);

        // move by one day
        for (int i = 0; i < 15; i++) {
            worldMap.move();
            System.out.println(worldMap.getPlants().values().size());
        }
        for (TreeSet<Animal> animalList : worldMap.getAnimals().values()) {
            for (Animal animal : animalList) {
                System.out.println(animal.getGenotype());
            }
        }


    }
}
