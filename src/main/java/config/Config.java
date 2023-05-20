package config;

import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.List;

public final class Config {

    private Config() {
    }

    public static final int BLOCK_SIZE = 35;
    public static final int MAP_SIZE = 15;
    public static final int MAP_WIDTH = 1000;
    public static final int MAP_HEIGHT = 700;

    ///BOARD
    public static final int BOARD_START_X = ((MAP_WIDTH / 2) - (600 / 2));
    public static final int BOARD_START_Y = ((MAP_HEIGHT / 2) - (383 / 2));
    public static final int BOARD_WIDTH = 800;
    public static final int BOARD_HEIGHT = 800;

    //STARTS POINT
    private static final int START_SPAWN_POINT_X_V1 = 200;
    private static final int START_SPAWN_POINT_X_V2 = START_SPAWN_POINT_X_V1 + 11 * BLOCK_SIZE;
    private static final int START_SPAWN_POINT_Y_V1 = 80;
    private static final int START_SPAWN_POINT_Y_V2 = START_SPAWN_POINT_Y_V1 + 11 * BLOCK_SIZE;

    //Red Player
    public static final Point2D SPAWN_POINT_RED_V1 = new Point2D(START_SPAWN_POINT_X_V1, START_SPAWN_POINT_Y_V1);
    public static final Point2D SPAWN_POINT_RED_V2 = new Point2D(START_SPAWN_POINT_X_V1, START_SPAWN_POINT_Y_V1 + BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_RED_V3 = new Point2D(START_SPAWN_POINT_X_V1 + BLOCK_SIZE, START_SPAWN_POINT_Y_V1 + BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_RED_V4 = new Point2D(START_SPAWN_POINT_X_V1 + BLOCK_SIZE, START_SPAWN_POINT_Y_V1);
    public static final List<Point2D> SPAWN_POINTS_RED = List.of(SPAWN_POINT_RED_V1, SPAWN_POINT_RED_V2, SPAWN_POINT_RED_V3, SPAWN_POINT_RED_V4);
    public static final Image RED_IDLE_IMG = FXGL.getAssetLoader().loadImage("player/1 Pink_Monster/Pink_Monster_Idle_7.png");
    public static final Image RED_RUN_IMG = FXGL.getAssetLoader().loadImage("player/1 Pink_Monster/Pink_Monster_Run_6.png");

    //Blue Player
    public static final Point2D SPAWN_POINT_BLUE_V1 = new Point2D(START_SPAWN_POINT_X_V2 + BLOCK_SIZE, START_SPAWN_POINT_Y_V1);
    public static final Point2D SPAWN_POINT_BLUE_V2 = new Point2D(START_SPAWN_POINT_X_V2 + BLOCK_SIZE, START_SPAWN_POINT_Y_V1 + BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_BLUE_V3 = new Point2D(START_SPAWN_POINT_X_V2, START_SPAWN_POINT_Y_V1 + BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_BLUE_V4 = new Point2D(START_SPAWN_POINT_X_V2, START_SPAWN_POINT_Y_V1);
    public static final List<Point2D> SPAWN_POINTS_BLUE = List.of(SPAWN_POINT_BLUE_V1, SPAWN_POINT_BLUE_V2, SPAWN_POINT_BLUE_V3, SPAWN_POINT_BLUE_V4);
    public static final Image BLUE_IDLE_IMG = FXGL.getAssetLoader().loadImage("player/3 Dude_Monster/Dude_Monster_Idle_7.png");
    public static final Image BLUE_RUN_IMG = FXGL.getAssetLoader().loadImage("player/3 Dude_Monster/Dude_Monster_Run_6.png");

    //Yellow Player
    public static final Point2D SPAWN_POINT_YELLOW_V1 = new Point2D(START_SPAWN_POINT_X_V2 + BLOCK_SIZE, START_SPAWN_POINT_Y_V2);
    public static final Point2D SPAWN_POINT_YELLOW_V2 = new Point2D(START_SPAWN_POINT_X_V2 + BLOCK_SIZE, START_SPAWN_POINT_Y_V2 + BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_YELLOW_V3 = new Point2D(START_SPAWN_POINT_X_V2, START_SPAWN_POINT_Y_V2 + BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_YELLOW_V4 = new Point2D(START_SPAWN_POINT_X_V2, START_SPAWN_POINT_Y_V2);
    public static final List<Point2D> SPAWN_POINTS_YELLOW = List.of(SPAWN_POINT_YELLOW_V1, SPAWN_POINT_YELLOW_V2, SPAWN_POINT_YELLOW_V3, SPAWN_POINT_YELLOW_V4);
    public static final Image YELLOW_IDLE_IMG = FXGL.getAssetLoader().loadImage("player/2 Owlet_Monster/Owlet_Monster_Idle_7.png");
    public static final Image YELLOW_RUN_IMG = FXGL.getAssetLoader().loadImage("player/2 Owlet_Monster/Owlet_Monster_Run_6.png");

    //Green Player
    public static final Point2D SPAWN_POINT_GREEN_V1 = new Point2D(START_SPAWN_POINT_X_V1, START_SPAWN_POINT_Y_V2);
    public static final Point2D SPAWN_POINT_GREEN_V2 = new Point2D(START_SPAWN_POINT_X_V1, START_SPAWN_POINT_Y_V2 + BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_GREEN_V3 = new Point2D(START_SPAWN_POINT_X_V1 + BLOCK_SIZE, START_SPAWN_POINT_Y_V2 + BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_GREEN_V4 = new Point2D(START_SPAWN_POINT_X_V1 + BLOCK_SIZE, START_SPAWN_POINT_Y_V2);
    public static final List<Point2D> SPAWN_POINTS_GREEN = List.of(SPAWN_POINT_GREEN_V1, SPAWN_POINT_GREEN_V2, SPAWN_POINT_GREEN_V3, SPAWN_POINT_GREEN_V4);
    public static final Image GREEN_IDLE_IMG = FXGL.getAssetLoader().loadImage("player/3 Dude_Monster/Dude_Monster_Idle_7.png");
    public static final Image GREEN_RUN_IMG = FXGL.getAssetLoader().loadImage("player/3 Dude_Monster/Dude_Monster_Run_6.png");

}
