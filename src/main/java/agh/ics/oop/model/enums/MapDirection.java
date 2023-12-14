package agh.ics.oop.model.enums;

import agh.ics.oop.model.Vector2d;

public enum MapDirection {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    public String toString() {
        return switch(this) {
            case NORTH -> "Północ";
            case NORTH_EAST -> "Północno-wschodni";
            case EAST -> "Wschód";
            case SOUTH_EAST -> "Południowo-wschodni";
            case SOUTH -> "Południe";
            case SOUTH_WEST -> "Południowo-zachodni";
            case WEST -> "Zachód";
            case NORTH_WEST -> "Północno-zachodni";
        };
    }

    public MapDirection next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    public MapDirection previous() {
        if (this.ordinal() - 1 < 0) {
            return values()[values().length - 1];
        }
        return values()[(this.ordinal() - 1) % values().length];
    }

    public Vector2d toUnitVector() {
        return switch(this) {
            case EAST -> new Vector2d(1,0);
            case WEST -> new Vector2d(-1,0);
            case NORTH -> new Vector2d(0,1);
            case SOUTH -> new Vector2d(0,-1);
            case NORTH_EAST -> new Vector2d(1,1);
            case NORTH_WEST -> new Vector2d(-1,1);
            case SOUTH_EAST -> new Vector2d(1,-1);
            case SOUTH_WEST -> new Vector2d(-1,-1);
        };
    }

    public MapDirection getNextDayDirection(int gene_number) {
        // gene number between 0 and 7
        if (gene_number < 0 || gene_number > 7) {
            throw new RuntimeException("Wrong gene number");
        }

        return values()[(this.ordinal() + gene_number) % values().length];
    }
}