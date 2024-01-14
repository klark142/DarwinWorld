package agh.ics.oop.presenter;

import agh.ics.oop.Configuration;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Pair;

import java.io.File;
import java.lang.reflect.WildcardType;
import java.util.Set;
import java.util.TreeSet;

public class MapPresenter implements MapChangeListener {
    public Label statsLabel;
    public Label statsAnimalLabel;
    public Button stopTrackingButton;
    public Button stopSimulationButton;
    public Button resumeSimulationButton;
    public Button showStatsButton;
    public Button hideStatsButton;
    public Button showGenotypeButton;
    public Button hideGenotypeButton;
    public Button showPreferredCellsButton;
    public Button hidePreferredCellsButton;
    public Button saveStatsButton;
    @FXML
    private Label messageLabel;
    @FXML
    private GridPane mapGrid;
    private SimulationEngine simulationEngine;
    private Simulation currentSimulation;
    private static final Color ANIMAL_COLOR = Color.SADDLEBROWN; // Brown color for animals
    private static final Color PLANT_COLOR = Color.web("#048012"); // Green color for plants
    private static final Color STEPPE_COLOR = Color.web("#d1af04");
    private static final Color EQUATOR_COLOR = Color.web("#61c944");


    private WorldMap worldMap;

    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }
    public void setSimulation(Simulation simulation) {
        this.currentSimulation = simulation;
    }

    public void setSimulationEngine(SimulationEngine simulationEngine) {
        this.simulationEngine = simulationEngine;
    }

    @FXML
    private void onStopSimulationClicked() {
        if (currentSimulation != null) {
            currentSimulation.pause();
            stopSimulationButton.setVisible(false);
            resumeSimulationButton.setVisible(true);
            showGenotypeButton.setVisible(true);
            showPreferredCellsButton.setVisible(true);
            saveStatsButton.setVisible(true);
        }
    }

    @FXML
    private void onResumeSimulationClicked() {
        if (currentSimulation != null) {
            currentSimulation.resume();
            stopSimulationButton.setVisible(true);
            resumeSimulationButton.setVisible(false);
            showGenotypeButton.setVisible(false);
            showPreferredCellsButton.setVisible(false);
            hidePreferredCellsButton.setVisible(false);
            saveStatsButton.setVisible(false);
        }
    }

    @FXML
    private void onShowStatsClicked() {
        String stats = worldMap.getStatistics().toString();
        statsLabel.setText(stats);
        statsLabel.setVisible(true);
        hideStatsButton.setVisible(true);
        showStatsButton.setVisible(false);
    }

    @FXML
    private void onHideStatsClicked() {
        statsLabel.setVisible(false);
        hideStatsButton.setVisible(false);
        showStatsButton.setVisible(true);
    }

    @FXML
    private void onShowGenotypeClicked() {
        // Logic to show animals with the most popular genotype
        // This might involve highlighting them on the map or listing them
        drawMapGenotypes(worldMap);
        hideGenotypeButton.setVisible(true);
        showGenotypeButton.setVisible(false);
    }

    @FXML
    private void onHideGenotypeClicked() {
        // Logic to hide the highlighted animals or list
        drawMap(worldMap);
        hideGenotypeButton.setVisible(false);
        showGenotypeButton.setVisible(true);
    }

    @FXML
    private void onShowPreferredCellsClicked() {
        drawMapPreferredCells(worldMap);
        hidePreferredCellsButton.setVisible(true);
        showPreferredCellsButton.setVisible(false);
    }

    @FXML
    private void onHidePreferredCellsClicked() {
        drawMap(worldMap);
        hidePreferredCellsButton.setVisible(false);
        showPreferredCellsButton.setVisible(true);
    }

    @FXML
    private void onSaveStatsClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Simulation Stats");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(null); // Replace 'null' with your Stage if available

        if (file != null) {
            // Logic to save simulation stats to the selected file
            worldMap.getStatistics().writeCSVToFile(file.getPath());
        }
    }

    private Label createColoredCell(String content, Color color) {
        Label label = new Label(content);
        label.setAlignment(Pos.CENTER);

        // set background color
        BackgroundFill fill = new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY);
        label.setBackground(new Background(fill));

        return label;
    }

    private Color getColorBasedOnEnergy(int energyPoints, int maxEnergy) {
        double brightness = mapEnergyToBrightness(energyPoints, maxEnergy);
        return ANIMAL_COLOR.deriveColor(0, 1, brightness, 1);
    }

    private double mapEnergyToBrightness(int energyPoints, int maxEnergy) {
        return 0.6 + (double) energyPoints / maxEnergy * 0.4;
    }

    public void drawMap(WorldMap worldMap) {
        clearGrid();
        Configuration configuration = worldMap.getConfiguration();
        Vector2d topRight = new Vector2d(configuration.getWidth(),
                configuration.getHeight());
        Vector2d bottomLeft = new Vector2d(0, 0);

        int gridWidth = topRight.getX() - bottomLeft.getX() + 2;
        int gridHeight = topRight.getY() - bottomLeft.getY() + 2;
        mapGrid.add(createLabel("y \\ x"), 0, 0);


        double CELL_WIDTH = mapGrid.getPrefWidth() / worldMap.getWidth();
        mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        double CELL_HEIGHT = mapGrid.getPrefHeight() / worldMap.getHeight();
        mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));



        // creating axis x and axis y

        int leftStart = bottomLeft.getX();

        for (int i = 1; i < gridWidth; i++) {
            mapGrid.add(createLabel(String.valueOf(leftStart)), i, 0);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
            leftStart++;
        }

        int upperStart = topRight.getY();
        for (int i = 1; i < gridHeight; i++) {
            mapGrid.add(createLabel(String.valueOf(upperStart)), 0, i);
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
            upperStart--;
        }

        int maxEnergy = worldMap.getMaxEnergyPoints();

        // filling map with actual objects
        for (int i = bottomLeft.getY(); i <= topRight.getY(); i++) {
            for (int j = bottomLeft.getX(); j <= topRight.getX(); j++) {
                Vector2d currPoint = new Vector2d(j, i);
                int columnInd = j - bottomLeft.getX() + 1;
                int rowInd = gridHeight - (i - bottomLeft.getY()) - 1;
                TreeSet<Animal> animals = worldMap.getAnimals().get(currPoint);

                // Initialize variables for content and color
                // check if empty cell will be on the equator or steppe
                Pair<Integer, Integer> rowRange = worldMap.getEquatorRows();
                Integer lower = rowRange.getKey();
                Integer upper = rowRange.getValue();
                Color cellColor;
                if (i >= lower && i <= upper) {
                    cellColor = EQUATOR_COLOR;
                } else {
                    cellColor = STEPPE_COLOR;
                }
                String content = "";

                if (animals != null && !animals.isEmpty()) {
                    // If there are animals, pick the first (or strongest) one
                    Animal animal = animals.first();
                    content = animal.toString();
                    cellColor = getColorBasedOnEnergy(animal.getEnergyPoints(), maxEnergy);

                    // Create the label and set the mouse click event for the animal
                    Label label = createColoredCell(content, cellColor);
                    label.setMinSize(CELL_WIDTH, CELL_HEIGHT);
                    label.setOnMouseClicked(event -> {
                        worldMap.getStatistics().startTrackingAnimal(animal);
                        showTrackingAnimalStats();
                        stopTrackingButton.setVisible(true);
                    });

                    mapGrid.add(label, columnInd, rowInd);
                } else {
                    // Check if there is a plant
                    Plant plant = worldMap.getPlants().get(currPoint);
                    if (plant != null) {
                        content = plant.toString();
                        cellColor = PLANT_COLOR;
                    }

                    // Create the label for a plant or empty cell
                    Label label = createColoredCell(content, cellColor);
                    label.setMinSize(CELL_WIDTH, CELL_HEIGHT);
                    mapGrid.add(label, columnInd, rowInd);
                }
            }
        }

        if (worldMap.getStatistics().isTrackingChosenAnimal()) {
            showTrackingAnimalStats();
        }
        String stats = worldMap.getStatistics().toString();
        statsLabel.setText(stats);
    }

    public void drawMapGenotypes(WorldMap worldMap) {
        clearGrid();
        Configuration configuration = worldMap.getConfiguration();
        Vector2d topRight = new Vector2d(configuration.getWidth(),
                configuration.getHeight());
        Vector2d bottomLeft = new Vector2d(0, 0);

        int gridWidth = topRight.getX() - bottomLeft.getX() + 2;
        int gridHeight = topRight.getY() - bottomLeft.getY() + 2;
        mapGrid.add(createLabel("y \\ x"), 0, 0);


        double CELL_WIDTH = mapGrid.getPrefWidth() / worldMap.getWidth();
        mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        double CELL_HEIGHT = mapGrid.getPrefHeight() / worldMap.getHeight();
        mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));



        // creating axis x and axis y

        int leftStart = bottomLeft.getX();

        for (int i = 1; i < gridWidth; i++) {
            mapGrid.add(createLabel(String.valueOf(leftStart)), i, 0);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
            leftStart++;
        }

        int upperStart = topRight.getY();
        for (int i = 1; i < gridHeight; i++) {
            mapGrid.add(createLabel(String.valueOf(upperStart)), 0, i);
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
            upperStart--;
        }

        int maxEnergy = worldMap.getMaxEnergyPoints();

        // filling map with actual objects
        for (int i = bottomLeft.getY(); i <= topRight.getY(); i++) {
            for (int j = bottomLeft.getX(); j <= topRight.getX(); j++) {
                Vector2d currPoint = new Vector2d(j, i);
                int columnInd = j - bottomLeft.getX() + 1;
                int rowInd = gridHeight - (i - bottomLeft.getY()) - 1;
                TreeSet<Animal> animals = worldMap.getAnimals().get(currPoint);

                // Initialize variables for content and color
                // check if empty cell will be on the equator or steppe
                Pair<Integer, Integer> rowRange = worldMap.getEquatorRows();
                Integer lower = rowRange.getKey();
                Integer upper = rowRange.getValue();
                Color cellColor;
                if (i >= lower && i <= upper) {
                    cellColor = EQUATOR_COLOR;
                } else {
                    cellColor = STEPPE_COLOR;
                }
                String content = "";

                if (animals != null && !animals.isEmpty()) {
                    // If there are animals, pick the first (or strongest) one
                    Animal animal = animals.first();
                    content = animal.toString();

                    cellColor = getColorBasedOnEnergy(animal.getEnergyPoints(), maxEnergy);
                    if (worldMap.getStatistics().getMostPopularGenotypeAnimals().contains(animal)) {
                        cellColor = Color.RED;
                    }

                    // Create the label and set the mouse click event for the animal
                    Label label = createColoredCell(content, cellColor);
                    label.setMinSize(CELL_WIDTH, CELL_HEIGHT);
                    label.setOnMouseClicked(event -> {
                        worldMap.getStatistics().startTrackingAnimal(animal);
                        showTrackingAnimalStats();
                        stopTrackingButton.setVisible(true);
                    });

                    mapGrid.add(label, columnInd, rowInd);
                } else {
                    // Check if there is a plant
                    Plant plant = worldMap.getPlants().get(currPoint);
                    if (plant != null) {
                        content = plant.toString();
                        cellColor = PLANT_COLOR;
                    }

                    // Create the label for a plant or empty cell
                    Label label = createColoredCell(content, cellColor);
                    label.setMinSize(CELL_WIDTH, CELL_HEIGHT);
                    mapGrid.add(label, columnInd, rowInd);
                }
            }
        }

        if (worldMap.getStatistics().isTrackingChosenAnimal()) {
            showTrackingAnimalStats();
        }
        String stats = worldMap.getStatistics().toString();
        statsLabel.setText(stats);
    }

    public void drawMapPreferredCells(WorldMap worldMap) {
        clearGrid();
        Configuration configuration = worldMap.getConfiguration();
        Vector2d topRight = new Vector2d(configuration.getWidth(),
                configuration.getHeight());
        Vector2d bottomLeft = new Vector2d(0, 0);

        int gridWidth = topRight.getX() - bottomLeft.getX() + 2;
        int gridHeight = topRight.getY() - bottomLeft.getY() + 2;
        mapGrid.add(createLabel("y \\ x"), 0, 0);


        double CELL_WIDTH = mapGrid.getPrefWidth() / worldMap.getWidth();
        mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        double CELL_HEIGHT = mapGrid.getPrefHeight() / worldMap.getHeight();
        mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));



        // creating axis x and axis y

        int leftStart = bottomLeft.getX();

        for (int i = 1; i < gridWidth; i++) {
            mapGrid.add(createLabel(String.valueOf(leftStart)), i, 0);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
            leftStart++;
        }

        int upperStart = topRight.getY();
        for (int i = 1; i < gridHeight; i++) {
            mapGrid.add(createLabel(String.valueOf(upperStart)), 0, i);
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
            upperStart--;
        }

        int maxEnergy = worldMap.getMaxEnergyPoints();
        Set<Vector2d> preferredCells = worldMap.getPreferredCells();

        // filling map with actual objects
        for (int i = bottomLeft.getY(); i <= topRight.getY(); i++) {
            for (int j = bottomLeft.getX(); j <= topRight.getX(); j++) {
                Vector2d currPoint = new Vector2d(j, i);
                int columnInd = j - bottomLeft.getX() + 1;
                int rowInd = gridHeight - (i - bottomLeft.getY()) - 1;
                TreeSet<Animal> animals = worldMap.getAnimals().get(currPoint);

                // Initialize variables for content and color
                // check if empty cell will be on the equator or steppe
                Pair<Integer, Integer> rowRange = worldMap.getEquatorRows();
                Integer lower = rowRange.getKey();
                Integer upper = rowRange.getValue();
                Color cellColor;
                if (i >= lower && i <= upper) {
                    cellColor = EQUATOR_COLOR;
                } else {
                    cellColor = STEPPE_COLOR;
                }
                String content = "";

                if (animals != null && !animals.isEmpty()) {
                    // If there are animals, pick the first (or strongest) one
                    Animal animal = animals.first();
                    content = animal.toString();
                    cellColor = getColorBasedOnEnergy(animal.getEnergyPoints(), maxEnergy);

                    // Create the label and set the mouse click event for the animal
                    Label label = createColoredCell(content, cellColor);
                    label.setMinSize(CELL_WIDTH, CELL_HEIGHT);
                    label.setOnMouseClicked(event -> {
                        worldMap.getStatistics().startTrackingAnimal(animal);
                        showTrackingAnimalStats();
                        stopTrackingButton.setVisible(true);
                    });

                    if (preferredCells.contains(currPoint)) {
                        label = createColoredCell("", Color.ORANGE);
                        label.setMinSize(CELL_WIDTH, CELL_HEIGHT);
                    }

                    mapGrid.add(label, columnInd, rowInd);
                } else {
                    // Check if there is a plant
                    Plant plant = worldMap.getPlants().get(currPoint);
                    if (plant != null) {
                        content = plant.toString();
                        cellColor = PLANT_COLOR;
                    }

                    // Create the label for a plant or empty cell
                    Label label = createColoredCell(content, cellColor);
                    label.setMinSize(CELL_WIDTH, CELL_HEIGHT);
                    if (preferredCells.contains(currPoint)) {
                        label = createColoredCell("", Color.ORANGE);
                        label.setMinSize(CELL_WIDTH, CELL_HEIGHT);
                    }
                    mapGrid.add(label, columnInd, rowInd);
                }
            }
        }

        if (worldMap.getStatistics().isTrackingChosenAnimal()) {
            showTrackingAnimalStats();
        }
        String stats = worldMap.getStatistics().toString();
        statsLabel.setText(stats);
    }

    private void showTrackingAnimalStats() {
        statsAnimalLabel.setText(worldMap.getStatistics().animalTrackingToString());
    }

    @FXML
    private void onStopTrackingClicked() {
        worldMap.getStatistics().stopTrackingAnimal();
        stopTrackingButton.setVisible(false);
        statsAnimalLabel.setText("");
    }


    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0));
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        GridPane.setHalignment(label, HPos.CENTER);
        return label;
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            drawMap(worldMap);
            messageLabel.setText(message);
        });
    }
}
