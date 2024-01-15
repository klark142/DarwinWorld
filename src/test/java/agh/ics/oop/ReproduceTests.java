package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.WorldMap;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReproduceTests {
//    JSONObject jsonObject = new JSONObject();
//    Configuration configuration = new Configuration(jsonObject);
//    WorldMap worldMap = new WorldMap(configuration);
    @Test
    void sumEnergy(){
        JSONObject jsonObject = new JSONObject();
        Configuration configuration = new Configuration(jsonObject);
        WorldMap worldMap = new WorldMap(configuration);
        Animal animal1 = new Animal(worldMap);
        Animal animal2 = new Animal(worldMap);
        if (animal1.canBreed() && animal2.canBreed()){
            Animal child = animal1.breed(animal2);
            int energyBefore = animal1.getEnergyPoints() + animal2.getEnergyPoints();
            int energyAfter = animal1.getEnergyPoints() + animal2.getEnergyPoints() + child.getEnergyPoints() - configuration.reproductionEnergyCost;
            assertEquals(energyBefore, energyAfter);
        }

    }

}
