package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener{
    private int updateCount = 0;

    @Override
    public synchronized void mapChanged(WorldMap worldMap, String message) {
        updateCount++;
        System.out.println("MapID: " + worldMap.getId());  //nowe
        System.out.println("Update #" + updateCount + ": " + message);
        System.out.println(worldMap);
    }
}
