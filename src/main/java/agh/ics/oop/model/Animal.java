package agh.ics.oop.model;

import agh.ics.oop.model.enums.MapDirection;

public class Animal implements WorldElement {
    private Vector2d position;
    private MapDirection animalDirection;
    private int energyPoints;
    private Genotype genotype;
    private final int animalNumber;

    public Animal(int animalNumber) {
        this.animalNumber = animalNumber;
    }

    public Vector2d getPosition() {
        return position;
    }

    public int getEnergyPoints() {
        return energyPoints;
    }

    public Genotype getGenotype() {
        return genotype;
    }
    public void move(MapDirection direction, MoveValidator validator){};
    public String toStringNr() {
        return "Animal" + animalNumber;
    }

}
