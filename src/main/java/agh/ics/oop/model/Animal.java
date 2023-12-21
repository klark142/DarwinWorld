package agh.ics.oop.model;

import agh.ics.oop.model.enums.MapDirection;

public class Animal {
    private Vector2d position;
    private MapDirection animalDirection;
    private int age;

    private int energyPoints;
    private int childrenNumber = 0;
    private Genotype genotype;
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

    public Animal(WorldMap worldMap) {
        this.worldMap = worldMap;
        this.height = 10;
        this.width = 10;
        this.animalDirection = MapDirection.getRandomDirection();
        this.genotype = new Genotype();
        this.toRemove = false;
        this.energyPoints = worldMap.getConfiguration().startAnimalEnergy;
    }

    // constructor for creating a new child
    public Animal(WorldMap worldMap, Vector2d position, Genotype genotype,
                  int energyPoints) {
        this.worldMap = worldMap;
        this.height = 10;
        this.width = 10;
        this.animalDirection = MapDirection.getRandomDirection();
        this.genotype = genotype;
        this.toRemove = false;
        this.energyPoints = energyPoints;
        this.position = position;
    }

    // constructor for copying animal objects
    public Animal(Animal other) {
        this.height = 10;
        this.width = 10;
        this.position = other.getPosition();
        this.animalDirection = other.getAnimalDirection();
        this.genotype = other.getGenotype();
        this.toRemove = false;
        this.worldMap = other.worldMap;
        this.energyPoints = worldMap.getConfiguration().startAnimalEnergy;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public Animal(WorldMap worldMap, Vector2d position) {
        this.worldMap = worldMap;
        this.height = 10;
        this.width = 10;
        this.position = position;
        this.animalDirection = MapDirection.getRandomDirection();
        this.genotype = new Genotype();
        this.toRemove = false;
        this.energyPoints = worldMap.getConfiguration().startAnimalEnergy;
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

        childrenNumber++;
        other.childrenNumber++;

        return child;
    }

    public boolean canBreed() {
        return getEnergyPoints() >= getWorldMap().getConfiguration().minimalReproductionEnergy;
    }
}
