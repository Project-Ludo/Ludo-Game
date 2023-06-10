package io.github.ludogame.config;

import javafx.geometry.Point2D;

public final class Config {

    private Config() {

    }

    public static final int BLOCK_SIZE = 33;
    public static final int MAP_SIZE = 15;
    public static final int MAP_WIDTH = 1100;
    public static final int MAP_HEIGHT = 700;

    public static final int MENU_START_LAYOUT_X = ((MAP_WIDTH / 2) - (800 / 2));
    public static final int MENU_START_LAYOUT_Y = ((MAP_HEIGHT / 2) - (500 / 2));

    public static final Point2D RED_PAWN_START_SPAWN_POINT = new Point2D(12,8);
    public static final Point2D GREEN_PAWN_START_SPAWN_POINT = new Point2D(6,12);
    public static final Point2D YELLOW_PAWN_START_SPAWN_POINT = new Point2D(8,2);
    public static final Point2D BLUE_PAWN_START_SPAWN_POINT = new Point2D(2,6);

}
