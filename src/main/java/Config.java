import javafx.geometry.Point2D;

public class Config {
    public static final int BLOCK_SIZE = 30;
    public static final int MAP_SIZE = 15;
    public static final int MAP_WIDTH = 800;
    public static final int MAP_HEIGHT = 600;

    //STARTS POINT
    private static final int START_SPAWN_POINT_X_V1 = 190;
    private static final int START_SPAWN_POINT_X_V2 = 520;
    private static final int START_SPAWN_POINT_Y_V1 = 80;
    private static final int START_SPAWN_POINT_Y_V2 = 410;

    //Red Player
    public static final Point2D SPAWN_POINT_RED_V1 = new Point2D(START_SPAWN_POINT_X_V1, START_SPAWN_POINT_Y_V1);
    public static final Point2D SPAWN_POINT_RED_V2 = new Point2D(START_SPAWN_POINT_X_V1, START_SPAWN_POINT_Y_V1 + BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_RED_V3 = new Point2D(START_SPAWN_POINT_X_V1 + BLOCK_SIZE, START_SPAWN_POINT_Y_V1 + BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_RED_V4 = new Point2D(START_SPAWN_POINT_X_V1 + BLOCK_SIZE, START_SPAWN_POINT_Y_V1);


    //Blue Player
    public static final Point2D SPAWN_POINT_BLUE_V1 = new Point2D(START_SPAWN_POINT_X_V2 + BLOCK_SIZE, START_SPAWN_POINT_Y_V1);
    public static final Point2D SPAWN_POINT_BLUE_V2 = new Point2D(START_SPAWN_POINT_X_V2 + BLOCK_SIZE, START_SPAWN_POINT_Y_V1 + BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_BLUE_V3 = new Point2D(START_SPAWN_POINT_X_V2, START_SPAWN_POINT_Y_V1 + BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_BLUE_V4 = new Point2D(START_SPAWN_POINT_X_V2, START_SPAWN_POINT_Y_V1);

    //Red Player
    public static final Point2D SPAWN_POINT_YELLOW_V1 = new Point2D(START_SPAWN_POINT_X_V2 + BLOCK_SIZE, START_SPAWN_POINT_Y_V2);
    public static final Point2D SPAWN_POINT_YELLOW_V2 = new Point2D(START_SPAWN_POINT_X_V2 + BLOCK_SIZE, START_SPAWN_POINT_Y_V2 + BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_YELLOW_V3 = new Point2D(START_SPAWN_POINT_X_V2, START_SPAWN_POINT_Y_V2 + BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_YELLOW_V4 = new Point2D(START_SPAWN_POINT_X_V2, START_SPAWN_POINT_Y_V2);

    //Red Player
    public static final Point2D SPAWN_POINT_GREEN_V1 = new Point2D(START_SPAWN_POINT_X_V1, START_SPAWN_POINT_Y_V2);
    public static final Point2D SPAWN_POINT_GREEN_V2 = new Point2D(START_SPAWN_POINT_X_V1, START_SPAWN_POINT_Y_V2 + BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_GREEN_V3 = new Point2D(START_SPAWN_POINT_X_V1 + BLOCK_SIZE, START_SPAWN_POINT_Y_V2 + BLOCK_SIZE);
    public static final Point2D SPAWN_POINT_GREEN_V4 = new Point2D(START_SPAWN_POINT_X_V1 + BLOCK_SIZE, START_SPAWN_POINT_Y_V2);
}
