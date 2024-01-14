package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.ConsoleMapDisplay;
import agh.ics.oop.model.StaticsCSV;
import agh.ics.oop.model.WorldMap;

import java.util.TreeSet;

public class Simulation implements Runnable {
    private final WorldMap map;
    private final Configuration configuration;
    private volatile boolean running = true;
    private volatile boolean paused = false;
    private boolean writeToCSV = false;
    private int refreshRate;

    public Simulation(Configuration configuration, WorldMap worldMap) {
        this.configuration = configuration;
        this.map = worldMap;
        ConsoleMapDisplay consoleMapDisplay = new ConsoleMapDisplay();
        map.registerMapChangeListener(consoleMapDisplay);
        map.placeRandomAnimals(configuration.getStartAnimalAmount());
        this.refreshRate = configuration.getRefreshRate();

//        for (TreeSet<Animal> animalList : map.getAnimals().values()) {
//            for (Animal animal : animalList) {
//                map.trackChosenAnimal(animal);
//                break;
//            }
//        }
    }

    public void setWriteToCSV(boolean writeToCSV) {
        this.writeToCSV = writeToCSV;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public boolean getWriteToCSV() {
        return writeToCSV;
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
        synchronized (this) {
            this.notify(); // Notify in case the simulation thread is waiting
        }
    }

    public void stop() {
        running = false;
        resume(); // In case it's paused, resume it to allow stopping
    }

//    public void run() {
//        for (int i = 0; i < 5; i++) {
//            this.map.updateMap();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
////            this.map.updateStaticsForFile();
////            synchronized (System.out) {
////                System.out.println(map.getStatistics());
////            }
//
//        }
//    }

    public void run() {
        Statistics statistics = this.map.getStatistics();
        while (running) {
            if (paused) {
                try {
                    synchronized (this) {
                        this.wait(); // Wait until resume is called
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return; // Exit if the thread is interrupted
                }
            }

            this.map.updateMap();
            String dailyStats = String.valueOf(this.map.getStatistics());
            statistics.addNewData(dailyStats);

            try {
                Thread.sleep(getRefreshRate()); // Sleep between iterations
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return; // Exit if the thread is interrupted
            }
        }
    }
}
