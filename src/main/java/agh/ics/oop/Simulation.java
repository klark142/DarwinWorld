package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.ConsoleMapDisplay;
import agh.ics.oop.model.StaticsCSV;
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
        StaticsCSV staticsCSV = new StaticsCSV(map);
        int nrDay = 1;
        for (int i = 0; i < 5; i++) {
            this.map.updateMap();
            String dailyStatics = String.valueOf(map.getStatistics());
            staticsCSV.addNewData("Day " + nrDay);
            staticsCSV.addNewData(dailyStatics);
            nrDay++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            staticsCSV.writeCSVToFile("C:\\Users\\Olek\\OneDrive\\Dokumenty\\test.csv");

//            this.map.updateStaticsForFile();
//            synchronized (System.out) {
//                System.out.println(map.getStatistics());
//            }

        }
    }
}
