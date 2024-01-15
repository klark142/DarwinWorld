package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Genotype;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.model.enums.MapDirection;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MovementTest {
    JSONObject jsonObject = new JSONObject();
    Configuration configuration = new Configuration(jsonObject);
    WorldMap worldMap = new WorldMap(configuration);
    @Test
    void upperBorder(){
        Vector2d position = new Vector2d(configuration.getWidth()/2, configuration.getHeight());
        List<Byte> zeroGenes = Collections.nCopies(1, (byte) 0);
        Genotype genotype = new Genotype(zeroGenes);
        Animal animal1 = new Animal(worldMap, position, genotype, 8);
        animal1.setAnimalDirection(MapDirection.NORTH);
        worldMap.placeAnimal(animal1, position, true);
        animal1.move(MapDirection.NORTH);
        MapDirection check = MapDirection.SOUTH;
        assertEquals(animal1.getAnimalDirection(), check);
    }
    @Test
    void lowerBorder(){
        Vector2d position = new Vector2d(configuration.getWidth()/2, 0);
        Animal animal1 = new Animal(worldMap, position);
        animal1.setAnimalDirection(MapDirection.SOUTH_EAST);
        worldMap.placeAnimal(animal1, position, true);
        animal1.move(MapDirection.SOUTH_EAST);
        MapDirection check = MapDirection.NORTH_WEST;
        assertEquals(animal1.getAnimalDirection(), check);
    }
    @Test
    void rightBorder(){
        Vector2d position = new Vector2d(configuration.getWidth(), configuration.getHeight()/2);
        Animal animal1 = new Animal(worldMap, position);
        animal1.setAnimalDirection(MapDirection.EAST);
        worldMap.placeAnimal(animal1, position, true);
        animal1.move(MapDirection.EAST);
        MapDirection checkDirection = MapDirection.EAST;
        Vector2d checkPosition = new Vector2d( 0,configuration.getHeight()/2);
        assertEquals(animal1.getAnimalDirection(), checkDirection);
        assertEquals(animal1.getPosition(),checkPosition);
    }
    @Test
    void leftBorder(){
        Vector2d position = new Vector2d(0, configuration.getHeight()/2);
        Animal animal1 = new Animal(worldMap, position);
        animal1.setAnimalDirection(MapDirection.SOUTH_WEST);
        worldMap.placeAnimal(animal1, position, true);
        animal1.move(MapDirection.SOUTH_WEST);
        MapDirection checkDirection = MapDirection.SOUTH_WEST;
        Vector2d checkPosition = new Vector2d( configuration.getWidth(),configuration.getHeight()/2 - 1);
        assertEquals(animal1.getAnimalDirection(), checkDirection);
        assertEquals(animal1.getPosition(),checkPosition);
    }
}
