package agh.ics.oop.presenter;

import agh.ics.oop.Configuration;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import agh.ics.oop.model.MoveDirection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.FileChooser;
import netscape.javascript.JSObject;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class SimulationPresenter implements MapChangeListener {
    public Button saveButton;
    public Button loadButton;
    private WorldMap worldMap;
    @FXML
    public ComboBox mapTypeCombo;
    @FXML
    public ComboBox animalBehaviourTypeCombo;
    @FXML
    private TextField widthField;
    @FXML
    private TextField heightField;
    @FXML
    private TextField energyPerPlantField;
    @FXML
    private TextField startPlantAmountField;
    @FXML
    private TextField plantsPerDayField;
    @FXML
    private TextField startAnimalAmountField;
    @FXML
    private TextField startAnimalEnergyField;
    @FXML
    private TextField minimalReproductionEnergyField;
    @FXML
    private TextField reproductionEnergyCostField;
    @FXML
    private TextField minimalMutationAmountField;
    @FXML
    private TextField maximumMutationAmountField;
    @FXML
    private TextField genotypeSizeField;


    @FXML
    private Label messageLabel;
    @FXML
    private GridPane mapGrid;

    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    @FXML
    public void onSimulationStartClicked() throws IOException {
        // convert user's input to json
        JSONObject jsonObject = convertInputToJSON();
        Configuration configuration = new Configuration(jsonObject);

        // create simulation objects
        List<Simulation> simulations = new ArrayList<>();
        WorldMap worldMap = new WorldMap(configuration);
        Simulation simulation = new Simulation(configuration, worldMap);
        simulations.add(simulation);

        // open new window
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("MapLayout.fxml"));
        BorderPane viewRoot = loader.load();

        MapPresenter mapPresenter = loader.getController();
        mapPresenter.setWorldMap(worldMap);
        worldMap.registerMapChangeListener(mapPresenter);

        Stage mapStage = new Stage();
        mapStage.setTitle("Map Window");
        Scene mapScene = new Scene(viewRoot);
        mapStage.setScene(mapScene);
        mapStage.show();

        // register listeners
        worldMap.registerMapChangeListener(mapPresenter);
        mapPresenter.setWorldMap(worldMap);

        // run
        SimulationEngine simulationEngine = new SimulationEngine(simulations);
        simulationEngine.runAsync();
    }

    public JSONObject convertInputToJSON() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("width", widthField.getText());
        jsonObject.put("height", heightField.getText());
        jsonObject.put("energyPerPlant", energyPerPlantField.getText());
        jsonObject.put("startPlantAmount", startPlantAmountField.getText());
        jsonObject.put("plantsPerDay", plantsPerDayField.getText());
        jsonObject.put("startAnimalAmount", startAnimalAmountField.getText());
        jsonObject.put("startAnimalEnergy", startAnimalEnergyField.getText());
        jsonObject.put("minimalReproductionEnergy", widthField.getText());
        jsonObject.put("reproductionEnergyCost", widthField.getText());
        jsonObject.put("minimalMutationAmount", widthField.getText());
        jsonObject.put("maximumMutationAmount", widthField.getText());
        jsonObject.put("genotypeSize", widthField.getText());

        // handle submit button action
        String selectedMapType = mapTypeCombo.getValue().toString();
        jsonObject.put("mapType", selectedMapType);

        String selectedBehaviourType = animalBehaviourTypeCombo.getValue().toString();
        jsonObject.put("animalBehaviourType", selectedBehaviourType);

        return jsonObject;
    }

    private void saveConfigurationToFile(JSONObject jsonObject) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON Files", "*.json"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        File file = fileChooser.showSaveDialog(null); // Replace 'null' with a reference to your Stage if available

        if (file != null) {
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(jsonObject.toString(4)); // Indentation for readability
            } catch (IOException e) {
                e.printStackTrace(); // Handle exception
            }
        }
    }

    @FXML
    private void onSaveClicked() {
        JSONObject jsonObject = convertInputToJSON();
        saveConfigurationToFile(jsonObject);
    }

    private JSONObject loadConfigurationFromFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Configuration File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = fileChooser.showOpenDialog(null); // Replace 'null' with a reference to your Stage if available

        if (file != null) {
            try {
                String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
                return new JSONObject(content);
            } catch (Exception e) {
                e.printStackTrace(); // Handle exception
            }
        }
        return null;
    }

    private void loadConfigurationToFields (JSONObject jsonObject) {
        if (jsonObject != null) {
            widthField.setText(jsonObject.optString("width", ""));
            heightField.setText(jsonObject.optString("height", ""));
            energyPerPlantField.setText(jsonObject.optString("energyPerPlant", ""));
            startPlantAmountField.setText(jsonObject.optString("startPlantAmount", ""));
            plantsPerDayField.setText(jsonObject.optString("plantsPerDay", ""));
            startAnimalAmountField.setText(jsonObject.optString("startAnimalAmount", ""));
            startAnimalEnergyField.setText(jsonObject.optString("startAnimalEnergy", ""));
            minimalReproductionEnergyField.setText(jsonObject.optString("minimalReproductionEnergy", ""));
            reproductionEnergyCostField.setText(jsonObject.optString("reproductionEnergyCost", ""));
            minimalMutationAmountField.setText(jsonObject.optString("minimalMutationAmount", ""));
            maximumMutationAmountField.setText(jsonObject.optString("maximumMutationAmount", ""));
            genotypeSizeField.setText(jsonObject.optString("genotypeSize", ""));

            // For ComboBoxes, you need to select the item that matches the value
            mapTypeCombo.getSelectionModel().select(jsonObject.optString("mapType"));
            animalBehaviourTypeCombo.getSelectionModel().select(jsonObject.optString("animalBehaviourType"));
        }
    }

    @FXML
    private void onLoadClicked() {
        JSONObject jsonObject = loadConfigurationFromFile();
        loadConfigurationToFields(jsonObject);
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {

    }


}
