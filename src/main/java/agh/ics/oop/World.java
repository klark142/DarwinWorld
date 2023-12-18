package agh.ics.oop;

import agh.ics.oop.model.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

public class World {
    //    public static void main(String[] args) {
//        // basic settings
//        int GENOTYPE_SIZE = 10;
//        Genotype.setGenotypeSize(GENOTYPE_SIZE);
//        Genotype genotype_first = new Genotype();
//        System.out.println(genotype_first);
//        Genotype genotype_second = new Genotype();
//        System.out.println(genotype_second);
//
//        Genotype genotype_child = genotype_first.crossGenotypes(genotype_second,
//                10, 20);
//        System.out.println(genotype_child);
//
//
//    }
    public static void main(String[] args) {


        Configuration configuration = new Configuration();

        // place animals on the map
        WorldMap worldMap = new WorldMap(configuration);
        System.out.println(worldMap);
        ConsoleMapDisplay consoleMapDisplay = new ConsoleMapDisplay();
        worldMap.registerMapChangeListener(consoleMapDisplay);
        worldMap.placeRandomAnimals(2);

        // move by one day
        worldMap.move();
        worldMap.move();
        worldMap.move();
        for (List<Animal> animalList : worldMap.getAnimals().values()) {
            for (Animal animal : animalList) {
                System.out.println(animal.getGenotype());
            }
        }

    }
}