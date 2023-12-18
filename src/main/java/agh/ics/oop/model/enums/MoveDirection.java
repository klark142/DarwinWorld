package agh.ics.oop.model;

public enum MoveDirection {
    FORWARD,
    FORWARD_RIGHT,
    RIGHT,
    RIGHT_BACKWARD,
    BACKWARD,
    LEFT_BACKWARD,
    LEFT,
    FORWARD_LEFT;

    public Vector2d toUnitVector() {
        return switch(this) {
            case FORWARD -> new Vector2d(0, 1);
            case FORWARD_RIGHT -> new Vector2d(1, 1);
            case RIGHT -> new Vector2d(1, 0);
            case RIGHT_BACKWARD -> new Vector2d(1, -1);
            case BACKWARD -> new Vector2d(0, -1);
            case LEFT_BACKWARD -> new Vector2d(-1, -1);
            case LEFT -> new Vector2d(-1, 0);
            case FORWARD_LEFT -> new Vector2d(-1, 1);
        };
    }
}