package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class World {
    public static void main(String[] args) {


        Configuration configuration = new Configuration();

        // place animals on the map
        WorldMap worldMap = new WorldMap(configuration);
        ConsoleMapDisplay consoleMapDisplay = new ConsoleMapDisplay();
        worldMap.registerMapChangeListener(consoleMapDisplay);
        worldMap.placeRandomAnimals(10);

        // move by one day
        for (int i = 0; i < 10; i++) {
            worldMap.updateMap();
            System.out.println(worldMap.getStatistics());
        }
        for (TreeSet<Animal> animalList : worldMap.getAnimals().values()) {
            for (Animal animal : animalList) {
                System.out.println(animal.getGenotype());
            }
        }
        System.out.println(Arrays.deepToString(worldMap.getArrayPlants()));
    }

}