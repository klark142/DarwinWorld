package agh.ics.oop;

import agh.ics.oop.Configuration;
import org.json.JSONException;
import org.json.JSONObject;

public class ConfigurationValidator {
    public static void validate(JSONObject jsonObject) {
        checkIntValue(jsonObject, "width");
        checkIntValue(jsonObject, "height");
        checkIntValue(jsonObject, "energyPerPlant");
        checkIntValue(jsonObject, "startPlantAmount");
        checkIntValue(jsonObject, "plantsPerDay");
        checkIntValue(jsonObject, "startAnimalAmount");
        checkIntValue(jsonObject, "startAnimalEnergy");
        checkIntValue(jsonObject, "minimalReproductionEnergy");
        checkIntValue(jsonObject, "reproductionEnergyCost");
        checkIntValue(jsonObject, "minimalMutationAmount");
        checkIntValue(jsonObject, "maximumMutationAmount");
        checkIntValue(jsonObject, "genotypeSize");
        checkIntValue(jsonObject, "refreshRate");
    }

//    private static void checkIntValue(JSONObject jsonObject, String key) {
//        if (!jsonObject.has(key)) {
//            throw new IllegalArgumentException("Brak klucza '" + key + "' w pliku konfiguracyjnym.");
//        }
//
//        Object value = jsonObject.getInt(key);
//        if (!(value instanceof Integer)) {
//            throw new IllegalArgumentException("Nieprawidlowy typ wartosci dla klucza '" + key + "'. Oczekiwano int. Wprowadzono " + value.getClass().getSimpleName());
//        }
//    }
    private static void checkIntValue(JSONObject jsonObject, String key) {
        if (!jsonObject.has(key)) {
            throw new IllegalArgumentException("Brak klucza '" + key + "' w pliku konfiguracyjnym.");
        }

        String valueString = jsonObject.getString(key);

        // Sprawdź, czy wartość zawiera tylko cyfry
        if (!valueString.matches("\\d+")) {
            throw new IllegalArgumentException("Nieprawidłowy format wartości dla klucza '" + key + "'. Oczekiwano liczby całkowitej.");
        }

    }

}
