package agh.ics.oop.presenter;

import agh.ics.oop.Configuration;
import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.TreeSet;

public class MapPresenter implements MapChangeListener {
    public Label statsLabel;
    public Button stopTrackingButton;
    @FXML
    private Label messageLabel;
    @FXML
    private GridPane mapGrid;

    private WorldMap worldMap;

    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
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


        int CELL_WIDTH = 40;
        mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        int CELL_HEIGHT = 40;
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

        // filling map with actual objects
        for (int i = bottomLeft.getY(); i <= topRight.getY(); i++) {
            for (int j = bottomLeft.getX(); j <= topRight.getX(); j++) {
                Vector2d currPoint = new Vector2d(j, i);
                if (worldMap.isOccupied(currPoint)) {
                    int columnInd = j - bottomLeft.getX() + 1;
                    int rowInd = gridHeight - (i - bottomLeft.getY()) - 1;
                    Label label = createLabel(worldMap.objectAt(currPoint).toString());
                    // if there is an animal on curr point
                    TreeSet<Animal> animals = worldMap.getAnimals().get(currPoint);
                    if (animals != null) {
                        Animal animal = animals.first();
                        label.setOnMouseClicked(event -> {
                            worldMap.getStatistics().startTrackingAnimal(animal);
                            showTrackingAnimalStats();
                            stopTrackingButton.setVisible(true);
                        });
                    }

                    mapGrid.add(label, columnInd, rowInd);
                }
            }
        }

        if (worldMap.getStatistics().isTrackingChosenAnimal()) {
            showTrackingAnimalStats();
        }
    }

    private void showTrackingAnimalStats() {
        statsLabel.setText("Animal Stats: " + worldMap.getStatistics().animalTrackingToString());
    }

    @FXML
    private void onStopTrackingClicked() {
        worldMap.getStatistics().stopTrackingAnimal();
        stopTrackingButton.setVisible(false);
        statsLabel.setText("");
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
