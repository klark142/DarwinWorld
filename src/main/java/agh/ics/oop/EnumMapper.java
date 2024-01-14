package agh.ics.oop;

import agh.ics.oop.model.enums.BehaviourType;
import agh.ics.oop.model.enums.MapType;

public class EnumMapper {
    public EnumMapper() {
    }

    public static MapType mapStringToMapType(String value) {
        return switch (value) {
            case "Normalny (preferowany równik)" -> MapType.EQUATOR_PREFERRED;
            case "Życiodajne truchła" -> MapType.LIFEGIVING_CORPSE;
            default -> throw new IllegalArgumentException("Unknown MapType: " + value);
        };
    }

    public static BehaviourType mapStringToBehaviourType(String value) {
        return switch (value) {
            case "Normalny" -> BehaviourType.NORMAL;
            case "Nieco szaleństwa" -> BehaviourType.CRAZY;
            default ->
                    throw new IllegalArgumentException("Unknown BehaviourType: " + value);
        };
    }
}
