package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.WorldMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeedTests {
    Configuration configuration = new Configuration();
    WorldMap worldMap = new WorldMap(configuration);
    @Test
    void feedPlant(){
        Animal animal1 = new Animal(worldMap);
        int energyTheoretical = animal1.getEnergyPoints() + configuration.getEnergyPerPlant();
        animal1.feed(configuration.getEnergyPerPlant());
        int energyPractical = animal1.getEnergyPoints();
        assertEquals(energyTheoretical, energyPractical);
    }
}
