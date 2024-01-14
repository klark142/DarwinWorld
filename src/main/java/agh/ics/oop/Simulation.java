package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.ConsoleMapDisplay;
import agh.ics.oop.model.WorldMap;

import java.util.TreeSet;

public class Simulation implements Runnable {
    private final WorldMap map;
    private final Configuration configuration;

    public Simulation(Configuration configuration, WorldMap worldMap) {
        this.configuration = configuration;
        this.map = worldMap;
        ConsoleMapDisplay consoleMapDisplay = new ConsoleMapDisplay();
        map.registerMapChangeListener(consoleMapDisplay);
        map.placeRandomAnimals(configuration.getStartAnimalAmount());

//        for (TreeSet<Animal> animalList : map.getAnimals().values()) {
//            for (Animal animal : animalList) {
//                map.trackChosenAnimal(animal);
//                break;
//            }
//        }
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            this.map.updateMap();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
//            this.map.updateStaticsForFile();
//            synchronized (System.out) {
//                System.out.println(map.getStatistics());
//            }

        }
    }
}
