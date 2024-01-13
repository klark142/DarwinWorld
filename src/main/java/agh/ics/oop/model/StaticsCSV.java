package agh.ics.oop.model;

import agh.ics.oop.Statistics;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class StaticsCSV {
    private final WorldMap map;

    public ArrayList<String> getStatistics() {
        return statistics;
    }

    private final ArrayList<String> statistics = new ArrayList<>();


    public StaticsCSV(WorldMap worldMap) {
        this.map = worldMap;
    }

    public void addNewData (String newData){
        statistics.add(newData);
    }

    public String generateCSVData() {

        StringBuilder csvData = new StringBuilder();
        csvData.append(statistics);

        return csvData.toString();
    }

    public void writeCSVToFile(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(generateCSVData());
            System.out.println("CSV file created successfully.");
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }
}
