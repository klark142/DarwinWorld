package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener {

    private int totalUpdates = 0;

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        totalUpdates++;

        System.out.println("Update #" + totalUpdates);
        System.out.println("Operation: " + message);
        System.out.println("Map state:");
        System.out.println(worldMap);
    }
}
