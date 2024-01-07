package agh.ics.oop.model;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class Genotype {
    protected List<Byte> genes;
    private Animal animal;
    // default genotype size value
    protected static int genotypeSize;
    private void generateGenotype() {
        Random random = new Random();
        for (int i = 0; i < genotypeSize; i++) {
            byte randomGene = (byte) random.nextInt(8);
            this.genes.add(randomGene);
        }
    }

    public static void setGenotypeSize(int length) {
        genotypeSize = length;
    }
    public Genotype(int genotypeSize, Animal animal) {
        this.genes = new ArrayList<>();
        this.animal = animal;
        setGenotypeSize(genotypeSize);
        generateGenotype();
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Genotype(List<Byte> genotype) {
        this.genes = genotype;
    }

    public Animal getAnimal() {
        return animal;
    }

    public byte getNextGene(int day) {
        return this.genes.get(day % genotypeSize);
    }
    public byte getNextGeneCrazy(int day) {
        Random random = new Random();
        float draw = random.nextFloat(1);

        if (draw < 0.8) {
            return this.genes.get(day % genotypeSize);
        } else {
            return this.genes.get(random.nextInt(Integer.MAX_VALUE) % genotypeSize);
        }
    }

    public Genotype crossGenotypes(Genotype other, int thisEnergy,
                                         int otherEnergy) {
        List<Byte> childGenotype = new ArrayList<>();

        int energySum = thisEnergy + otherEnergy;
        int thisAmount = (int) Math.ceil((thisEnergy / (double) energySum) * genotypeSize);
        int otherAmount = genotypeSize - thisAmount;

        Random random = new Random();
        float draw = random.nextFloat(); // Generates a random float between 0 (inclusive) and 1 (exclusive)

        int strongerEnergy = Math.max(thisEnergy, otherEnergy);

        if (draw < 0.5) {
            // Left side
            List<Byte> strongerParentGenes = (strongerEnergy == thisEnergy) ? this.genes : other.genes;
            List<Byte> weakerParentGenes = (strongerEnergy == thisEnergy) ? other.genes : this.genes;

            childGenotype.addAll(strongerParentGenes.subList(0, thisAmount));
            childGenotype.addAll(weakerParentGenes.subList(genotypeSize - otherAmount, genotypeSize));
        } else {
            // Right side
            List<Byte> strongerParentGenes = (strongerEnergy == thisEnergy) ? this.genes : other.genes;
            List<Byte> weakerParentGenes = (strongerEnergy == thisEnergy) ? other.genes : this.genes;

            childGenotype.addAll(strongerParentGenes.subList(genotypeSize - thisAmount, genotypeSize));
            childGenotype.addAll(weakerParentGenes.subList(0, otherAmount));
        }

        // mutation
        int mutationAmount = random.nextInt(genotypeSize);
        Set<Integer> uniqueNumbers = new HashSet<>();

        while (uniqueNumbers.size() < mutationAmount) {
            int randomIndex = random.nextInt(genotypeSize);
            uniqueNumbers.add(randomIndex);
        }

        for (Integer index : uniqueNumbers) {
            childGenotype.set(index, (byte) random.nextInt(8));
        }

        return new Genotype(childGenotype);
    }

    public String toString() {
        return this.genes.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Genotype genotype = (Genotype) obj;
        return Objects.equals(genes, genotype.genes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genes);
    }

}
