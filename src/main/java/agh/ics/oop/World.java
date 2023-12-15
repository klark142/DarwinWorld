package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.Vector;

public class World {
    //    public static void main(String[] args) {
//        // basic settings
//        int GENOTYPE_SIZE = 10;
//        Genotype.setGenotypeSize(GENOTYPE_SIZE);
//        Genotype genotype_first = new Genotype();
//        System.out.println(genotype_first);
//        Genotype genotype_second = new Genotype();
//        System.out.println(genotype_second);
//
//        Genotype genotype_child = genotype_first.crossGenotypes(genotype_second,
//                10, 20);
//        System.out.println(genotype_child);
//
//
//    }
    public static void main(String[] args) {
        Vector2d position1 = new Vector2d(0, 0);
        Vector2d position2 = new Vector2d(0, 1);

        java.util.List<Vector2d> corpses = java.util.List.of(position1, position2);

//        WorldMap testMap = new EquatorPrefered(10, 10, 10);
//        testMap.GrassOnMapExtra(10, corpses);
//        System.out.println(testMap);
//        for (int i = 0; i < 3; i++) {
//            testMap.GrassOnMapExtra(5, corpses);
//            System.out.println(testMap);
//        }

        WorldMap testMap = new LifegivingCorpse(10,10,10);
        testMap.GrassOnMapExtra(3, corpses);
        System.out.println(testMap);
    }
}