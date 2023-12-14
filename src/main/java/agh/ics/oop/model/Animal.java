package agh.ics.oop.model;

import agh.ics.oop.model.enums.MapDirection;

public class Animal {
    private Vector2d position;
    private MapDirection animalDirection;
    private int energyPoints;
    private Genotype genotype;

    public Vector2d getPosition() {
        return position;
    }

    public int getEnergyPoints() {
        return energyPoints;
    }

    public Genotype getGenotype() {
        return genotype;
    }
}
