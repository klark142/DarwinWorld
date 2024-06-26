package agh.ics.oop;

import agh.ics.oop.model.enums.BehaviourType;
import agh.ics.oop.model.enums.MapType;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Configuration {
//    public void setWidth(int width) {
//        this.width = width;
//    }


//    public Configuration(JSONObject jsonObject) {
//        loadFromJson(jsonObject);
//    }

    public Configuration() {
    }

    public int width = 10;
    public int height = 10;
    public int energyPerPlant = 3;
    public int startPlantAmount = 5;
    public int plantsPerDay = 2;
    public int startAnimalAmount = 5;
    public int startAnimalEnergy = 10;
    public int minimalReproductionEnergy = 2;
    public int reproductionEnergyCost = 3;
    public int minimalMutationAmount = 0;
    public int maximumMutationAmount = 0;
    public MapType mapType = MapType.EQUATOR_PREFERRED;
    public BehaviourType animalBehaviourType = BehaviourType.NORMAL;
    public int genotypeSize = 10;
    public int refreshRate;

    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public int getPlantsPerDay() {
        return plantsPerDay;
    }

    public int getGenotypeSize() {
        return genotypeSize;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getEnergyPerPlant() {
        return energyPerPlant;
    }

    public int getStartPlantAmount() {
        return startPlantAmount;
    }

    public int getStartAnimalAmount() {
        return startAnimalAmount;
    }

    public int getStartAnimalEnergy() {
        return startAnimalEnergy;
    }

    public int getMinimalReproductionEnergy() {
        return minimalReproductionEnergy;
    }

    public int getReproductionEnergyCost() {
        return reproductionEnergyCost;
    }

    public int getMinimalMutationAmount() {
        return minimalMutationAmount;
    }

    public int getMaximumMutationAmount() {
        return maximumMutationAmount;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setEnergyPerPlant(int energyPerPlant) {
        this.energyPerPlant = energyPerPlant;
    }

    public void setStartPlantAmount(int startPlantAmount) {
        this.startPlantAmount = startPlantAmount;
    }

    public void setPlantsPerDay(int plantsPerDay) {
        this.plantsPerDay = plantsPerDay;
    }

    public void setStartAnimalAmount(int startAnimalAmount) {
        this.startAnimalAmount = startAnimalAmount;
    }

    public void setStartAnimalEnergy(int startAnimalEnergy) {
        this.startAnimalEnergy = startAnimalEnergy;
    }

    public void setMinimalReproductionEnergy(int minimalReproductionEnergy) {
        this.minimalReproductionEnergy = minimalReproductionEnergy;
    }

    public void setReproductionEnergyCost(int reproductionEnergyCost) {
        this.reproductionEnergyCost = reproductionEnergyCost;
    }

    public void setMinimalMutationAmount(int minimalMutationAmount) {
        this.minimalMutationAmount = minimalMutationAmount;
    }

    public void setMaximumMutationAmount(int maximumMutationAmount) {
        this.maximumMutationAmount = maximumMutationAmount;
    }

    public void setMapType(MapType mapType) {
        this.mapType = mapType;
    }

    public void setAnimalBehaviourType(BehaviourType animalBehaviourType) {
        this.animalBehaviourType = animalBehaviourType;
    }

    public void setGenotypeSize(int genotypeSize) {
        this.genotypeSize = genotypeSize;
    }

    public MapType getMapType() {
        return mapType;
    }

    public BehaviourType getAnimalBehaviourType() {
        return animalBehaviourType;
    }

    public void loadFromJson(JSONObject jsonObject, SimulationPresenter simulationPresenter) {
        try {
            setWidth(jsonObject.getInt("width"));
            setHeight(jsonObject.getInt("height"));
            setEnergyPerPlant(jsonObject.getInt("energyPerPlant"));
            setStartPlantAmount(jsonObject.getInt("startPlantAmount"));
            setPlantsPerDay(jsonObject.getInt("plantsPerDay"));
            setStartAnimalAmount(jsonObject.getInt("startAnimalAmount"));
            setStartAnimalEnergy(jsonObject.getInt("startAnimalEnergy"));
            setMinimalReproductionEnergy(jsonObject.getInt("minimalReproductionEnergy"));
            setReproductionEnergyCost(jsonObject.getInt("reproductionEnergyCost"));
            setMinimalMutationAmount(jsonObject.getInt("minimalMutationAmount"));
            setMaximumMutationAmount(jsonObject.getInt("maximumMutationAmount"));
            setGenotypeSize(jsonObject.getInt("genotypeSize"));
            setRefreshRate(jsonObject.getInt("refreshRate"));

            MapType mapType = EnumMapper.mapStringToMapType(jsonObject.getString("mapType"));
            setMapType(mapType);

            BehaviourType behaviourType = EnumMapper.mapStringToBehaviourType(jsonObject.getString("animalBehaviourType"));
            setAnimalBehaviourType(behaviourType);
//        } catch (JSONException e) {
//            simulationPresenter.handleJsonException(e);
        } catch (Exception e) {
            throw new JSONException(e.toString());
        }
    }

    private void handleOtherException(Exception e) {
        System.out.println("Error: " + e.getMessage());
    }




}
