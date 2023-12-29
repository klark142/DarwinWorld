package agh.ics.oop.model;
import agh.ics.oop.Configuration;
import agh.ics.oop.Statistics;
import agh.ics.oop.model.enums.MapDirection;
import agh.ics.oop.model.enums.MapType;
import agh.ics.oop.model.util.AnimalComparator;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public class WorldMap {
    private Map<Vector2d, TreeSet<Animal>> animals;
    private Map<Vector2d, Plant> plants;
    private int height;
    private int width;
    private IPlants plantsMap;
    private List<MapChangeListener> mapChangeListeners;
    private int plantsPerDay;
    private static int currentDay = 0;
    private Configuration configuration;
    private Statistics statistics;
    private Set<Animal> deadAnimals;
    private int [][] arrayPlant;


    public WorldMap(Configuration configuration) {
        this.configuration = configuration;
        this.animals = new HashMap<>();
        this.height = configuration.getHeight();
        this.width = configuration.getWidth();
        this.mapChangeListeners = new ArrayList<>();

        if (getConfiguration().getMapType() == MapType.EQUATOR_PREFERRED) {
            this.plantsMap = new EquatorPreferred(this);
            this.arrayPlant = EquatorPreferred.getPlantAmount();
        } else {
            this.plantsMap = new LifegivingCorpse(this);
            this.arrayPlant = LifegivingCorpse.getPlantAmount();
        }

        plantsMap.placePlants(getConfiguration().getStartPlantAmount(), new ArrayList<>());
        this.plantsPerDay = configuration.getPlantsPerDay();
        this.statistics = new Statistics(this);
        this.deadAnimals = new HashSet<>();
    }
    public int [][]getArrayPlants(){
        return arrayPlant;
    }

    public static int getCurrentDay() {
        return currentDay;
    }

    public Set<Animal> getDeadAnimals() {
        return deadAnimals;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Map<Vector2d, Plant> getPlants() {
        return plants;
    }

    public int getHeight() {
        return height;
    }
    public Statistics getStatistics() {
        return statistics;
    }

    public Map<Vector2d, TreeSet<Animal>> getAnimals() {
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
            Animal animal = new Animal(this, position);
            placeAnimal(animal, position, true);
        }
    }

    public void placeAnimal(Animal animal, Vector2d position, Boolean mapChanged) {
        // if this position contains other objects
        if (getAnimals().containsKey(position)) {
            getAnimals().get(position).add(animal);
        } else {
            // if this position is not used
            TreeSet<Animal> positionAnimals = new TreeSet<>(new AnimalComparator());
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
        for (TreeSet<Animal> animalList : getAnimals().values()) {
            for (Animal animalOld : animalList) {
                MapDirection newDirection = MapDirection.getNextDirection(
                        animalOld.getAnimalDirection(),
                        animalOld.getGenotype().getNextGene(getCurrentDay()));

                animalOld.setToRemove(true);
                Animal animalNew = new Animal(animalOld);
                animalNew.setEnergyPoints(animalNew.getEnergyPoints() - 1);
                animalNew.move(newDirection);
                animalNew.setAnimalDirection(newDirection);
                animalsToAdd.add(animalNew);
            }
        }

        if (animalsToAdd.isEmpty()) {
            throw  new RuntimeException("All animals are dead");
        }
        removeOldAnimals();
        addNewAnimals(animalsToAdd);
    }

    private void addDailyPlants() {
        plantsMap.placePlants(this.plantsPerDay, new ArrayList<>());
    }

    public void addNewAnimals(List<Animal> animals) {
        for (Animal animal : animals) {
            placeAnimal(animal, animal.getPosition(), false);
            animal.setAge(animal.getAge() + 1);
        }
        mapChanged(currentDay + 1 + " day ended");
    }

    public void removeOldAnimals() {
        Iterator<Map.Entry<Vector2d, TreeSet<Animal>>> iterator = getAnimals().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Vector2d, TreeSet<Animal>> entry = iterator.next();
            TreeSet<Animal> animalList = entry.getValue();

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

    public void feedAnimals() {
        Set<Plant> plantsToRemove = new HashSet<>();
        for (Plant plant : getPlants().values()) {
            TreeSet<Animal> animalsAtPosition = getAnimals().get(plant.getPosition());
            if (animalsAtPosition != null && !animalsAtPosition.isEmpty()) {
                animalsAtPosition.first().feed(getConfiguration().energyPerPlant);
                plantsToRemove.add(plant);
            }
        }

        // delete eaten plants
        Iterator<Map.Entry<Vector2d, Plant>> iterator = getPlants().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Vector2d, Plant> entry = iterator.next();
            Plant plant = entry.getValue();

            if (plantsToRemove.contains(plant)) {
                iterator.remove();
            }
        }
    }

    public void breedAnimals() {
        for (Vector2d position : getAnimals().keySet()) {
            TreeSet<Animal> animalsAtPosition = getAnimals().get(position);

            if (animalsAtPosition.size() < 2) {
                continue;
            }
            // get two first animals to breed
            Animal firstParent = animalsAtPosition.pollLast();
            Animal secondParent = animalsAtPosition.pollLast();

            // remove parents from main map
            getAnimals().get(position).pollLast();
            getAnimals().get(position).pollLast();

            assert firstParent != null;
            Animal child = firstParent.breed(secondParent);

            if (child == null) {
                placeAnimal(firstParent, firstParent.getPosition(), false);
                assert secondParent != null;
                placeAnimal(secondParent, secondParent.getPosition(), false);
                continue;
            };

            // add parents to the main map
            placeAnimal(firstParent, child.getPosition(), false);
            placeAnimal(secondParent, child.getPosition(), false);
            placeAnimal(child, child.getPosition(), true);
            statistics.recordAnimalBirth();
        }
    }


    public void removeDeadAnimals() {
        Iterator<Map.Entry<Vector2d, TreeSet<Animal>>> iterator = getAnimals().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Vector2d, TreeSet<Animal>> entry = iterator.next();
            TreeSet<Animal> animalList = entry.getValue();

            // iterate over the list of animals
            Iterator<Animal> animalIterator = animalList.iterator();
            while (animalIterator.hasNext()) {
                Animal animal = animalIterator.next();
                if (animal.getEnergyPoints() <= 0) {
                    animal.setDayOfDeath(currentDay);
                    deadAnimals.add(animal);
                    animalIterator.remove();
                }
            }

            if (animalList.isEmpty()) {
                iterator.remove();
            }
        }
    }

    // stats functions

    public void updateFreeFields() {
        int freeFields = 0;
        for (int x = 0; x <= getWidth(); x++) {
            for (int y = 0; y <= getHeight(); y++) {
                Vector2d field = new Vector2d(x, y);
                if (objectAt(field) == null) {
                    freeFields++;
                }
            }
        }
        statistics.setFreeFields(freeFields);
    }

    public int getTotalAnimals() {
        int totalAnimals = 0;
        for (TreeSet<Animal> treeSet: getAnimals().values()) {
            totalAnimals += treeSet.size();
        }
        return totalAnimals;
    }

    public int getTotalPlants() {
        return getPlants().size();
    }

    // main daily function

    public void updateMap() {
        addDailyPlants();
        removeDeadAnimals();
        checkAnimalsDead();
        move();
        feedAnimals();
        breedAnimals();
        currentDay++;
        updateStats();
    }

    private void updateStats() {
        getStatistics().setTotalAnimals(getTotalAnimals());
        getStatistics().setTotalPlants(getTotalPlants());
        // TODO free fields debugging
        updateFreeFields();
        getStatistics().updateAverageEnergy();
        getStatistics().updateAverageLifespan();
        getStatistics().updateAverageChildren();
        // TODO popular genotypes
    }

    private void checkAnimalsDead() {
        boolean noAnimals = true;
        for (TreeSet<Animal> treeSet : getAnimals().values()) {
            if (!treeSet.isEmpty()) {
                noAnimals = false;
                break;
            }
        }
        if (noAnimals) {
            throw new RuntimeException("All Animals are dead");
        }
    }

    public void updateMapFirstDay() {
        removeDeadAnimals();
        checkAnimalsDead();
        move();
        feedAnimals();
        breedAnimals();
        currentDay++;
        updateStats();
    }
}
