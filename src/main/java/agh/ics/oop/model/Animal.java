package agh.ics.oop.model;

import agh.ics.oop.model.enums.MapDirection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Animal {
    // animal statistics
    private Genotype genotype;
    private MapDirection activatedGene;
    private int energyPoints;
    private int eatenPlantsNumber;
    private int childrenNumber;
    private int descendantsNumber;
    private int age;
    private int dayOfDeath;
    private Set<Animal> children;

    // TODO descendants logic

    // map related attributes
    private Vector2d position;
    private MapDirection animalDirection;
    private int height;
    private int width;
    private boolean toRemove;
    private WorldMap worldMap;

    public Vector2d getPosition() {
        return position;
    }

    public int getEnergyPoints() {
        return energyPoints;
    }

    public int getAge() {
        return age;
    }

    public void setActivatedGene(MapDirection activatedGene) {
        this.activatedGene = activatedGene;
    }

    public int getChildrenNumber() {
        return childrenNumber;
    }

    public void setEnergyPoints(int energyPoints) {
        this.energyPoints = energyPoints;
    }

    public WorldMap getWorldMap() {
        return worldMap;
    }

    public Genotype getGenotype() {
        return genotype;
    }
    public MapDirection getAnimalDirection() {
        return this.animalDirection;
    }

    public void setAnimalDirection(MapDirection animalDirection) {
        this.animalDirection = animalDirection;
    }

    public void setChildrenNumber(int childrenNumber) {
        this.childrenNumber = childrenNumber;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDayOfDeath(int dayOfDeath) {
        this.dayOfDeath = dayOfDeath;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setToRemove(boolean toRemove) {
        this.toRemove = toRemove;
    }

    public boolean isToRemove() {
        return toRemove;
    }
    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public int getEatenPlantsNumber() {
        return eatenPlantsNumber;
    }

    public MapDirection getActivatedGene() {
        return activatedGene;
    }

    public int getDayOfDeath() {
        return dayOfDeath;
    }

    public Animal(WorldMap worldMap) {
        this.worldMap = worldMap;
        this.height = worldMap.getConfiguration().getHeight();
        this.width = worldMap.getConfiguration().getWidth();
        this.animalDirection = MapDirection.getRandomDirection();
        this.genotype = new Genotype(worldMap.getConfiguration().genotypeSize, this);
        this.toRemove = false;
        this.energyPoints = worldMap.getConfiguration().startAnimalEnergy;
        this.children = new HashSet<>();
    }

    // constructor for creating a new child
    public Animal(WorldMap worldMap, Vector2d position, Genotype genotype,
                  int energyPoints) {
        this.worldMap = worldMap;
        this.height = worldMap.getConfiguration().getHeight();
        this.width = worldMap.getConfiguration().getWidth();
        this.animalDirection = MapDirection.getRandomDirection();
        this.genotype = genotype;
        this.toRemove = false;
        this.energyPoints = energyPoints;
        this.position = position;
        this.children = new HashSet<>();
    }

    // constructor for copying animal objects
    public Animal(Animal other) {
        this.worldMap = other.worldMap;
        this.height = worldMap.getConfiguration().getHeight();
        this.width = worldMap.getConfiguration().getWidth();
        this.position = other.getPosition();
        this.animalDirection = other.getAnimalDirection();
        this.genotype = other.getGenotype();
        this.genotype.setAnimal(other);
        this.toRemove = false;
        this.energyPoints = other.getEnergyPoints();
        this.eatenPlantsNumber = other.getEatenPlantsNumber();
        this.childrenNumber = other.getChildrenNumber();
        this.age = other.getAge();
        this.children = other.children;
        this.activatedGene = other.getActivatedGene();
        this.descendantsNumber = other.getDescendantsNumber();
    }

    public Animal(WorldMap worldMap, Vector2d position) {
        this.worldMap = worldMap;
        this.height = worldMap.getConfiguration().getHeight();
        this.width = worldMap.getConfiguration().getWidth();
        this.position = position;
        this.animalDirection = MapDirection.getRandomDirection();
        this.genotype = new Genotype(worldMap.getConfiguration().genotypeSize, this);
        this.toRemove = false;
        this.energyPoints = worldMap.getConfiguration().startAnimalEnergy;
        this.children = new HashSet<>();
    }

    public void addChild(Animal child) {
        this.children.add(child);
    }
    public String toString() {
        return switch (getAnimalDirection()) {
            case NORTH -> "N";
            case NORTH_EAST -> "NE";
            case EAST -> "E";
            case SOUTH_EAST -> "SE";
            case SOUTH -> "S";
            case SOUTH_WEST -> "SW";
            case WEST -> "W";
            case NORTH_WEST -> "NW";
        };
    }

    public int getDescendantsNumber() {
        int count = 0;
        for (Animal child : children) {
            count += child.getDescendantsNumber() + 1;
        }
        return count;
    }

    public void updateDescendantsNumber() {
        this.descendantsNumber = this.getDescendantsNumber();
    }

    public Set<Animal> getChildren() {
        return children;
    }

    private Vector2d newMovePosition(MapDirection mapDirection) {
        // new position
        int newX = getPosition().getX() + mapDirection.toUnitVector().getX();
        int newY = getPosition().getY() + mapDirection.toUnitVector().getY();
        if (newX > getWidth()) {
            newX = 0;
        } else if (newX < 0) {
            newX = getWidth();
        }
        if (newY > getHeight() || newY < 0) {
            newY = getPosition().getY();
            setAnimalDirection(MapDirection.getOpposite(getAnimalDirection()));
        }
        return new Vector2d(newX, newY);
    }

    public void move(MapDirection mapDirection) {
        // moves always forward based on current animal's map direction
        Vector2d newPosition = newMovePosition(mapDirection);
        setPosition(newPosition);
    }

    public void feed(int energyPoints) {
        setEnergyPoints(getEnergyPoints() + energyPoints);
        eatenPlantsNumber++;
    }

    public Animal breed(Animal other) {
        if (!canBreed() || !other.canBreed()) return null;

        int energyLoss = getWorldMap().getConfiguration().reproductionEnergyCost;

        // create genome of the child
        Genotype childGenotype = this.getGenotype().crossGenotypes(other.getGenotype(),
                getEnergyPoints(), other.getEnergyPoints());


        // decrease energy
        setEnergyPoints(getEnergyPoints() - energyLoss);
        other.setEnergyPoints(getEnergyPoints() - energyLoss);

        // create child object
        Animal child = new Animal(getWorldMap(), getPosition(), childGenotype,
                energyLoss);
        childGenotype.setAnimal(child);

        // update children stats
        childrenNumber++;
        addChild(child);
        other.childrenNumber++;
        other.addChild(child);

        return child;
    }

    public boolean canBreed() {
        return getEnergyPoints() >= getWorldMap().getConfiguration().minimalReproductionEnergy;
    }
}
