package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class World {
    public static void main(String[] args) throws InterruptedException {


        Configuration configuration = new Configuration();

        // place animals on the map
//        WorldMap worldMap = new WorldMap(configuration);
//        ConsoleMapDisplay consoleMapDisplay = new ConsoleMapDisplay();
//        worldMap.registerMapChangeListener(consoleMapDisplay);
//        worldMap.placeRandomAnimals(10);
//
//        for (TreeSet<Animal> animalList : worldMap.getAnimals().values()) {
//            for (Animal animal : animalList) {
//                worldMap.trackChosenAnimal(animal);
//                break;
//            }
//        }
//            // move by one day
//        for (int i = 0; i < 25; i++) {
//            worldMap.updateMap();
//            System.out.println(worldMap.getStatistics());
//        }
        List<Simulation> simulations = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            simulations.add(new Simulation(configuration, new WorldMap(configuration)));
        }
        SimulationEngine simulationEngine = new SimulationEngine(simulations);
        simulationEngine.runAsync();
        simulationEngine.awaitSimulationsEnd();
    }
}