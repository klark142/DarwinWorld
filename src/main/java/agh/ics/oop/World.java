package agh.ics.oop;

import agh.ics.oop.model.Genotype;

public class World {
    public static void main(String[] args) {
        // basic settings
        int GENOTYPE_SIZE = 10;
        Genotype.setGenotypeSize(GENOTYPE_SIZE);
        Genotype genotype_first = new Genotype();
        System.out.println(genotype_first);
        Genotype genotype_second = new Genotype();
        System.out.println(genotype_second);

        Genotype genotype_child = genotype_first.crossGenotypes(genotype_second,
                10, 20);
        System.out.println(genotype_child);


    }
}
