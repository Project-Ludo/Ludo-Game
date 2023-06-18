package io.github.ludogame.config;

import javafx.geometry.Point2D;

import java.util.Arrays;
import java.util.LinkedList;

public final class Config {

    private Config() {
    }

    public static final int BLOCK_SIZE = 33;
    public static final int MAP_SIZE = 15;
    public static final int MAP_WIDTH = 1100;
    public static final int MAP_HEIGHT = 700;

    public static final int MENU_START_LAYOUT_X = ((MAP_WIDTH / 2) - (800 / 2));
    public static final int MENU_START_LAYOUT_Y = ((MAP_HEIGHT / 2) - (500 / 2));

    public static final Point2D RED_PAWN_START_SPAWN_POINT = new Point2D(21, 11);
    public static final Point2D GREEN_PAWN_START_SPAWN_POINT = new Point2D(15, 15);
    public static final Point2D YELLOW_PAWN_START_SPAWN_POINT = new Point2D(17, 5);
    public static final Point2D BLUE_PAWN_START_SPAWN_POINT = new Point2D(11, 9);
    public static final Point2D BLUE_PAWN_START_SPAWN_POINT_PIXELS = new Point2D(
            BLUE_PAWN_START_SPAWN_POINT.getX() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2,
            BLUE_PAWN_START_SPAWN_POINT.getY() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2
    );
    public static final Point2D RED_PAWN_START_SPAWN_POINT_PIXELS = new Point2D(
            RED_PAWN_START_SPAWN_POINT.getX() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2,
            RED_PAWN_START_SPAWN_POINT.getY() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2
    );

    public static final Point2D GREEN_PAWN_START_SPAWN_POINT_PIXELS = new Point2D(
            GREEN_PAWN_START_SPAWN_POINT.getX() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2,
            GREEN_PAWN_START_SPAWN_POINT.getY() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2
    );

    public static final Point2D YELLOW_PAWN_START_SPAWN_POINT_PIXELS = new Point2D(
            YELLOW_PAWN_START_SPAWN_POINT.getX() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2,
            YELLOW_PAWN_START_SPAWN_POINT.getY() * Config.BLOCK_SIZE + Config.BLOCK_SIZE/2
    );

    public static final Point2D RED_PAWN_FINISH_POINT = new Point2D(17, 10);
    public static final Point2D GREEN_PAWN_FINISH_POINT = new Point2D(16, 11);
    public static final Point2D YELLOW_PAWN_FINISH_POINT = new Point2D(16, 9);
    public static final Point2D BLUE_PAWN_FINISH_POINT = new Point2D(15, 10);

    public static final LinkedList<Point2D> BLUE_PATH = new LinkedList<>(Arrays.asList(
            new Point2D(11, 9),
            new Point2D(12, 9),
            new Point2D(13, 9),
            new Point2D(14, 9),
            new Point2D(15, 9),
            new Point2D(15, 8),
            new Point2D(15, 7),
            new Point2D(15, 6),
            new Point2D(15, 5),
            new Point2D(15, 4),
            new Point2D(16, 4),
            new Point2D(17, 4),
            new Point2D(17, 5),
            new Point2D(17, 6),
            new Point2D(17, 7),
            new Point2D(17, 8),
            new Point2D(17, 9),
            new Point2D(18, 9),
            new Point2D(19, 9),
            new Point2D(20, 9),
            new Point2D(21, 9),
            new Point2D(22, 9),
            new Point2D(22, 10),
            new Point2D(22, 11),
            new Point2D(21, 11),
            new Point2D(20, 11),
            new Point2D(19, 11),
            new Point2D(18, 11),
            new Point2D(17, 11),
            new Point2D(17, 12),
            new Point2D(17, 13),
            new Point2D(17, 14),
            new Point2D(17, 15),
            new Point2D(17, 16),
            new Point2D(16, 16),
            new Point2D(15, 16),
            new Point2D(15, 15),
            new Point2D(15, 14),
            new Point2D(15, 13),
            new Point2D(15, 12),
            new Point2D(15, 11),
            new Point2D(14, 11),
            new Point2D(13, 11),
            new Point2D(12, 11),
            new Point2D(11, 11),
            new Point2D(10, 11),
            new Point2D(10, 10),
            new Point2D(11, 10),
            new Point2D(12, 10),
            new Point2D(13, 10),
            new Point2D(14, 10),
            new Point2D(15, 10)
    ));

    public static final LinkedList<Point2D> YELLOW_PATH = new LinkedList<>(Arrays.asList(
            new Point2D(17, 5),
            new Point2D(17, 6),
            new Point2D(17, 7),
            new Point2D(17, 8),
            new Point2D(17, 9),
            new Point2D(18, 9),
            new Point2D(19, 9),
            new Point2D(20, 9),
            new Point2D(21, 9),
            new Point2D(22, 9),
            new Point2D(22, 10),
            new Point2D(22, 11),
            new Point2D(21, 11),
            new Point2D(20, 11),
            new Point2D(19, 11),
            new Point2D(18, 11),
            new Point2D(17, 11),
            new Point2D(17, 12),
            new Point2D(17, 13),
            new Point2D(17, 14),
            new Point2D(17, 15),
            new Point2D(17, 16),
            new Point2D(16, 16),
            new Point2D(15, 16),
            new Point2D(15, 15),
            new Point2D(15, 14),
            new Point2D(15, 13),
            new Point2D(15, 12),
            new Point2D(15, 11),
            new Point2D(14, 11),
            new Point2D(13, 11),
            new Point2D(12, 11),
            new Point2D(11, 11),
            new Point2D(10, 11),
            new Point2D(10, 10),
            new Point2D(10, 9),
            new Point2D(11, 9),
            new Point2D(12, 9),
            new Point2D(13, 9),
            new Point2D(14, 9),
            new Point2D(15, 9),
            new Point2D(15, 8),
            new Point2D(15, 7),
            new Point2D(15, 6),
            new Point2D(15, 5),
            new Point2D(15, 4),
            new Point2D(16, 4),
            new Point2D(16, 5),
            new Point2D(16, 6),
            new Point2D(16, 7),
            new Point2D(16, 8),
            new Point2D(16, 9)
    ));

    public static final LinkedList<Point2D> RED_PATH = new LinkedList<>(Arrays.asList(
            new Point2D(21, 11),
            new Point2D(20, 11),
            new Point2D(19, 11),
            new Point2D(18, 11),
            new Point2D(17, 11),
            new Point2D(17, 12),
            new Point2D(17, 13),
            new Point2D(17, 14),
            new Point2D(17, 15),
            new Point2D(17, 16),
            new Point2D(16, 16),
            new Point2D(15, 16),
            new Point2D(15, 15),
            new Point2D(15, 14),
            new Point2D(15, 13),
            new Point2D(15, 12),
            new Point2D(15, 11),
            new Point2D(14, 11),
            new Point2D(13, 11),
            new Point2D(12, 11),
            new Point2D(11, 11),
            new Point2D(10, 11),
            new Point2D(10, 10),
            new Point2D(10, 9),
            new Point2D(11, 9),
            new Point2D(12, 9),
            new Point2D(13, 9),
            new Point2D(14, 9),
            new Point2D(15, 9),
            new Point2D(15, 8),
            new Point2D(15, 7),
            new Point2D(15, 6),
            new Point2D(15, 5),
            new Point2D(15, 4),
            new Point2D(16, 4),
            new Point2D(17, 4),
            new Point2D(17, 5),
            new Point2D(17, 6),
            new Point2D(17, 7),
            new Point2D(17, 8),
            new Point2D(17, 9),
            new Point2D(18, 9),
            new Point2D(19, 9),
            new Point2D(20, 9),
            new Point2D(21, 9),
            new Point2D(22, 9),
            new Point2D(22, 10),
            new Point2D(21, 10),
            new Point2D(20, 10),
            new Point2D(19, 10),
            new Point2D(18, 10),
            new Point2D(17, 10)
    ));

    public static final LinkedList<Point2D> GREEN_PATH = new LinkedList<>(Arrays.asList(
            new Point2D(15, 15),
            new Point2D(15, 14),
            new Point2D(15, 13),
            new Point2D(15, 12),
            new Point2D(15, 11),
            new Point2D(14, 11),
            new Point2D(13, 11),
            new Point2D(12, 11),
            new Point2D(11, 11),
            new Point2D(10, 11),
            new Point2D(10, 10),
            new Point2D(10, 9),
            new Point2D(11, 9),
            new Point2D(12, 9),
            new Point2D(13, 9),
            new Point2D(14, 9),
            new Point2D(15, 9),
            new Point2D(15, 8),
            new Point2D(15, 7),
            new Point2D(15, 6),
            new Point2D(15, 5),
            new Point2D(15, 4),
            new Point2D(16, 4),
            new Point2D(17, 4),
            new Point2D(17, 5),
            new Point2D(17, 6),
            new Point2D(17, 7),
            new Point2D(17, 8),
            new Point2D(17, 9),
            new Point2D(18, 9),
            new Point2D(19, 9),
            new Point2D(20, 9),
            new Point2D(21, 9),
            new Point2D(22, 9),
            new Point2D(22, 10),
            new Point2D(22, 11),
            new Point2D(21, 11),
            new Point2D(20, 11),
            new Point2D(19, 11),
            new Point2D(18, 11),
            new Point2D(17, 11),
            new Point2D(17, 12),
            new Point2D(17, 13),
            new Point2D(17, 14),
            new Point2D(17, 15),
            new Point2D(17, 16),
            new Point2D(16, 16),
            new Point2D(16, 15),
            new Point2D(16, 14),
            new Point2D(16, 13),
            new Point2D(16, 12),
            new Point2D(16, 11)
    ));
}
