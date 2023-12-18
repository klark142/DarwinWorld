package agh.ics.oop.model;
import agh.ics.oop.Configuration;
import agh.ics.oop.MapSettings;
import agh.ics.oop.model.enums.MapDirection;
import agh.ics.oop.model.enums.MapType;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class WorldMap {
    private Map<Vector2d, List<Animal>> animals;
    private Map<Vector2d, Plant> plants;
    private int height;
    private int width;
    private IPlants plantsMap;
    private List<MapChangeListener> mapChangeListeners;
    private int plantsPerDay;
    private static int currentDay = 0;


    public WorldMap(Configuration configuration) {
        this.animals = new HashMap<>();
        this.height = configuration.getHeight();
        this.width = configuration.getWidth();
        this.mapChangeListeners = new ArrayList<>();

        if (configuration.getMapType() == MapType.EQUATOR_PREFERRED) {
            this.plantsMap = new EquatorPreferred(this);
        } else {
            this.plantsMap = new LifegivingCorpse(this);
        }

        plantsMap.placePlants(configuration.getStartPlantAmount(), new ArrayList<>());
        this.plantsPerDay = configuration.getPlantsPerDay();
    }

    public static int getCurrentDay() {
        return currentDay;
    }

    public int getHeight() {
        return height;
    }

    public Map<Vector2d, List<Animal>> getAnimals() {
        return this.animals;
    }

    public int getWidth() {
        return width;
    }

    public Vector2d getLowerLeft() {
        return new Vector2d(0, 0);
    }

    public Vector2d getUpperRight() {
        return new Vector2d(getWidth(), getHeight());
    }

    public String toString() {
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        return mapVisualizer.draw(getLowerLeft(), getUpperRight());
    }


    private void mapChanged(String message) {
        for (MapChangeListener listener : mapChangeListeners) {
            listener.mapChanged(this, message);
        }
    }

    public void registerMapChangeListener(MapChangeListener listener) {
        mapChangeListeners.add(listener);
    }

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    public Object objectAt(Vector2d position) {
        // return list of objects at position
        // check animal objects
        List<Object> positionObjects = new ArrayList<>();
        for (Vector2d currPos : getAnimals().keySet()) {
            if (currPos.equals(position)) {
                positionObjects.add(getAnimals().get(position));
            }
        }

        // check plant objects
        plants = plantsMap.getPlants();
        for (Vector2d currPos : plants.keySet()) {
            if (currPos.equals(position)) {
                positionObjects.add(plants.get(position));
            }
        }

        if (!positionObjects.isEmpty()) {
            return positionObjects;
        }
        return null;
    }

    public void placeRandomAnimals(int animalNumber) {
        Random random = new Random();
        for (int i = 0; i < animalNumber; i++) {
            // map index draw
            int x = random.nextInt(getWidth());
            int y = random.nextInt(getHeight());
            Vector2d position = new Vector2d(x, y);
            Animal animal = new Animal(position);
            placeAnimal(animal, position, true);
        }
    }

    public void placeAnimal(Animal animal, Vector2d position, Boolean mapChanged) {
        // if this position contains other objects
        if (getAnimals().containsKey(position)) {
            getAnimals().get(position).add(animal);
        } else {
            // if this position is not used
            List<Animal> positionAnimals = new ArrayList<>();
            positionAnimals.add(animal);
            getAnimals().put(position, positionAnimals);
        }
        animal.setPosition(position);
        if (mapChanged) {
            mapChanged("Animal has been placed at " + animal.getPosition());
        }
    }

    public void move() {
        // moves all animals on the map by 1 position (1 simulation day)
        List<Animal> animalsToAdd = new ArrayList<>();
        for (List<Animal> animalList : getAnimals().values()) {
            for (Animal animalOld : animalList) {
                MapDirection newDirection = MapDirection.getNextDirection(
                        animalOld.getAnimalDirection(),
                        animalOld.getGenotype().getNextGene(getCurrentDay()));

                animalOld.setToRemove(true);
                Animal animalNew = new Animal(animalOld);
                animalNew.move(newDirection);
                animalNew.setAnimalDirection(newDirection);
                animalsToAdd.add(animalNew);
//                mapChanged("Animal has just moved from " + animalOld.getPosition() + " " +
//                        "to " + animalNew.getPosition());
//                placeAnimal(animalNew, animalNew.getPosition());
            }
        }


        removeOldAnimals();
        addNewAnimals(animalsToAdd);
        plantsMap.placePlants(this.plantsPerDay, new ArrayList<>());
        currentDay++;
    }

    public void addNewAnimals(List<Animal> animals) {
        for (Animal animal : animals) {
            placeAnimal(animal, animal.getPosition(), false);
        }
        mapChanged(currentDay + 1 + " day ended");
    }

    public void removeOldAnimals() {
        Iterator<Map.Entry<Vector2d, List<Animal>>> iterator = getAnimals().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Vector2d, List<Animal>> entry = iterator.next();
            List<Animal> animalList = entry.getValue();

            // iterate over the list of animals
            Iterator<Animal> animalIterator = animalList.iterator();
            while (animalIterator.hasNext()) {
                Animal animal = animalIterator.next();
                if (animal.isToRemove()) {
                    animalIterator.remove();
                }
            }

            if (animalList.isEmpty()) {
                iterator.remove();
            }
        }
    }

    public void removeAnimal(Animal animal) {
        Vector2d position = animal.getPosition();
        List<Animal> animalList = getAnimals().get(position);
        animalList.remove(animal);
    }

}
