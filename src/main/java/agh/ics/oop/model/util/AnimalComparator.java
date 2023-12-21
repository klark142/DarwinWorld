package agh.ics.oop.model.util;

import agh.ics.oop.model.Animal;

import java.util.Comparator;

public class AnimalComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal animal1, Animal animal2) {
        if (animal1.getEnergyPoints() != animal2.getEnergyPoints()) {
            return Integer.compare(animal1.getEnergyPoints(), animal2.getEnergyPoints());
        }

        if (animal1.getAge() != animal2.getAge()) {
            return Integer.compare(animal1.getAge(), animal2.getAge());
        }

        if (animal1.getChildrenNumber() != animal2.getChildrenNumber()) {
            return Integer.compare(animal1.getChildrenNumber(),
                    animal2.getChildrenNumber());
        }

        return Math.random() < 0.5 ? -1 : 1;
    }
}
